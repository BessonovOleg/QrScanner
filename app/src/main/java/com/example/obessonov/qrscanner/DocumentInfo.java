package com.example.obessonov.qrscanner;


public class DocumentInfo {
    private int doc_id;             //ID
    private String doc_date;        //Дата
    private String doc_no;          //номер документа
    private String doc_name;        //наименование документа
    private String doc_ps1;         //клиент
    private String doc_memo;        //примечание
    private String doc_done;        //проведенность
    private String txtResultCheck;  //результат проверки
    private String doc_sum;         //сумма

    public void setDoc_sum(String doc_sum) {
        this.doc_sum = doc_sum;
    }

    public String getDoc_sum() {

        return doc_sum;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public void setDoc_date(String doc_date) {
        this.doc_date = doc_date;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public void setDoc_ps1(String doc_ps1) {
        this.doc_ps1 = doc_ps1;
    }

    public void setDoc_memo(String doc_memo) {
        this.doc_memo = doc_memo;
    }

    public void setDoc_done(String doc_done) {
        this.doc_done = doc_done;
    }

    public void setTxtResultCheck(String txtResultCheck) {
        this.txtResultCheck = txtResultCheck;
    }

    public int getDoc_id() {

        return doc_id;
    }

    public String getDoc_date() {
        return doc_date;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getDoc_ps1() {
        return doc_ps1;
    }

    public String getDoc_memo() {
        return doc_memo;
    }

    public String getDoc_done() {
        return doc_done;
    }

    public String getTxtResultCheck() {
        return txtResultCheck;
    }
}
