package com.example.quizzer;

public class QuestionModel {
    private String quest,optA,optB,optC,optD,ans;
    private int setNo;

    public QuestionModel(){

    }

    public QuestionModel(String quest, String optA, String optB, String optC, String optD, String ans, int setNo) {
        this.quest = quest;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.ans = ans;
        this.setNo=setNo;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getOptA() {
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptB() {
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptC() {
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptD() {
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public int getSetNo() {
        return setNo;
    }

    public void setSetNo(int setNo) {
        this.setNo = setNo;
    }
}
