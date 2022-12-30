package com.example.notetakingapp;

public class Note {

    private String Name;
    private String Description;
    

        public Note(String Name, String Description){
            this.Description = Description;
            this.Name = Name;
        }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString(){
            return Name+": "+Description;
    }
}
