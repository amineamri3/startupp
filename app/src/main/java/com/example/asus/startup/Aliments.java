package com.example.asus.startup;

    public class Aliments {
        boolean isSelected;
        String userName;

        //now create constructor and getter setter method using shortcut like command+n for mac & Alt+Insert for window.


        public Aliments(boolean isSelected, String userName) {
            this.isSelected = isSelected;
            this.userName = userName;
        }


        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getName() {
            return userName;
        }

        public void setName(String userName) {
            this.userName = userName;
        }
}
