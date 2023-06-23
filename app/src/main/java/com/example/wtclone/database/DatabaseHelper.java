package com.example.wtclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wtclone.models.Message;
import com.example.wtclone.R;
import com.example.wtclone.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "chat_database";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CHATS = "chats";
    private static final String TABLE_MESSAGES = "messages";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_CHAT_ID = "chat_id";
    private static final String KEY_IS_SENT = "is_sent";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(createUserTable);

        String createChatTable = "CREATE TABLE " + TABLE_CHATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + "FOREIGN KEY(" + KEY_NAME + ") REFERENCES " + TABLE_USERS + "(" + KEY_NAME + ")" + ")";
        db.execSQL(createChatTable);

        String createMessageTable = "CREATE TABLE " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CHAT_ID + " INTEGER,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_IS_SENT + " INTEGER,"
                + "FOREIGN KEY(" + KEY_CHAT_ID + ") REFERENCES " + TABLE_CHATS + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")" + ")";
        db.execSQL(createMessageTable);

        for (int i = 1; i <= 50; i++) {
            ContentValues user = new ContentValues();
            user.put(KEY_ID, i);
            user.put(KEY_NAME, "User " + i);
            db.insert(TABLE_USERS, null, user);

            ContentValues chat = new ContentValues();
            chat.put(KEY_ID, i);
            chat.put(KEY_NAME, "Chat " + i);
            db.insert(TABLE_CHATS, null, chat);

            for(int j = 0; j < 10; j++){
                ContentValues message = new ContentValues();
                message.put(KEY_CHAT_ID, i);
                message.put(KEY_USER_ID, i);
                message.put(KEY_MESSAGE, "Hello, this is a test message number " + j);
                message.put(KEY_IS_SENT, j % 2);
                db.insert(TABLE_MESSAGES, null, message);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public List<Chat> getAllChats() {
        List<Chat> chatList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHATS + " c INNER JOIN "
                + TABLE_USERS + " u ON u." + KEY_ID + " = c." + KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int image = R.drawable.baseline_person_24;
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));

                String messageQuery = "SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_CHAT_ID + " = ? AND " + KEY_USER_ID + " = ? ORDER BY " + KEY_ID + " DESC LIMIT 1";
                Cursor sentMessageCursor = db.rawQuery(messageQuery, new String[]{ String.valueOf(id), "1" });
                Cursor receivedMessageCursor = db.rawQuery(messageQuery, new String[]{ String.valueOf(id), String.valueOf(id) });

                String sentMessage = null;
                String receivedMessage = null;
                if(sentMessageCursor.moveToFirst()){
                    sentMessage = sentMessageCursor.getString(sentMessageCursor.getColumnIndex(KEY_MESSAGE));
                }
                if(receivedMessageCursor.moveToFirst()){
                    receivedMessage = receivedMessageCursor.getString(receivedMessageCursor.getColumnIndex(KEY_MESSAGE));
                }
                chatList.add(new Chat(id, image, name, sentMessage, receivedMessage));
                sentMessageCursor.close();
                receivedMessageCursor.close();
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chatList;
    }

    public List<Message> getMessagesForChat(int chatId) {
        List<Message> messageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_CHAT_ID + " = ?", new String[]{ String.valueOf(chatId) });

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndex(KEY_USER_ID));
                String text = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
                boolean isSent = cursor.getInt(cursor.getColumnIndex(KEY_IS_SENT)) == 1;

                messageList.add(new Message(text, isSent,userId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return messageList;
    }

    public Chat getChat(int chatId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHATS,
                new String[] {KEY_ID, KEY_NAME},
                KEY_ID + "=?",
                new String[] {String.valueOf(chatId)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount() == 0)
            return null;

        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));

        String messageQuery = "SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_CHAT_ID + " = ? AND " + KEY_USER_ID + " = ? ORDER BY " + KEY_ID + " DESC LIMIT 1";
        Cursor sentMessageCursor = db.rawQuery(messageQuery, new String[]{ String.valueOf(id), "1" });
        Cursor receivedMessageCursor = db.rawQuery(messageQuery, new String[]{ String.valueOf(id), String.valueOf(id) });

        String sentMessage = null;
        String receivedMessage = null;
        if(sentMessageCursor.moveToFirst()){
            sentMessage = sentMessageCursor.getString(sentMessageCursor.getColumnIndex(KEY_MESSAGE));
        }
        if(receivedMessageCursor.moveToFirst()){
            receivedMessage = receivedMessageCursor.getString(receivedMessageCursor.getColumnIndex(KEY_MESSAGE));
        }

        int image = R.drawable.baseline_person_24; // replace with your actual image

        Chat chat = new Chat(id, image, name, sentMessage, receivedMessage);

        sentMessageCursor.close();
        receivedMessageCursor.close();
        cursor.close();

        return chat;
    }



}

