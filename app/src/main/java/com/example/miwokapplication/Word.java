package com.example.miwokapplication;

public class Word {
    private String mEnglishWord;
    private String mMiwokWord;
    private int mImageId;
    private int mAudioId;

    public Word(String mEnglishWord, String mMiwokWord, int mImageId, int mAudioId) {
        this.mEnglishWord = mEnglishWord;
        this.mMiwokWord = mMiwokWord;
        this.mImageId = mImageId;
        this.mAudioId = mAudioId;
    }

    public Word(String mEnglishWord, String mMiwokWord, int mAudioId) {
        this.mEnglishWord = mEnglishWord;
        this.mMiwokWord = mMiwokWord;
        this.mAudioId = mAudioId;
    }

    public String getmEnglishWord() {
        return mEnglishWord;
    }

    public String getmMiwokWord() {
        return mMiwokWord;
    }

    public int getmImageId() {
        return mImageId;
    }

    public int getmAudioId() {
        return mAudioId;
    }
}
