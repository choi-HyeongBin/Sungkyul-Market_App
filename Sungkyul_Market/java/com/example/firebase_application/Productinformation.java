package com.example.firebase_application;

public class Productinformation {

        public String imv;
        public String uid;

        public String spinner2;
        public String name;
        public String money;
        public String text;
        public String state;
        public String useremail;
        public String data;
        public String date;
        public String category;
        public String receiveemail;
        public String sendemail;


        public void setState(String state) {
                this.state = state;
        }

        public String getUseremail() {
                return useremail;
        }

        public void setUseremail(String useremail) {
                this.useremail = useremail;
        }


        public String getReceiveemail() {
                return receiveemail;
        }

        public void setReceiveemail(String receiveemail) {
                this.receiveemail = receiveemail;
        }


        public String getSendemail() {
                return sendemail;
        }

        public void setSendemail(String sendemail) {
                this.sendemail = sendemail;
        }

        public String getData() {
                return data;
        }

        public void setData(String data) {
                this.data = data;
        }

        public Productinformation(String name, String money, String text) {
        }

        public Productinformation() {

        }

        public String getSpinner2() {
                return spinner2;
        }

        public void setSpinner2(String spinner2) {
                this.spinner2 = spinner2;
        }

        public String getState(){return state;}

        public String getName() {
                return name;
        }

        public String setName(String name) {
                this.name = name;
                return name;
        }

        public String getMoney() {
                return money;
        }

        public void setMoney(String money) {
                this.money = money;
        }

        public String getText() {
                return text;
        }
        public void setText(String text) {
                this.text = text;
        }
        public String getDate() {
                return date;
        }
        public void setDate(String date) {
                this.date= date;
        }

        public String getImv() {
                return imv;
        }

        public void setImv(String imv) {
                this.imv = imv;
        }


        public String getUid() {
                return uid;
        }

        public void setUid(String uid) {
                this.uid = uid;
        }

        public void setCategory(String category) {
                this.category = category;
        }
        public String getCategory() {
                return category;
        }


}