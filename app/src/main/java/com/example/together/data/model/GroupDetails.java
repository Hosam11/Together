package com.example.together.data.model;

import java.util.ArrayList;

public class GroupDetails {

private String name ;
private String description;
private String status;
private int duration;
private String interest;
private ArrayList<Member> members=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public class Member
    {
        private String address;

        private String gender;

        private String created_at;

        private String photo;

        private String admin;

        private String email_verified_at;

        private String updated_at;

        private int group_id;

        private String name;

        private Pivot pivot;

        private int id;

        private String email;

        private String BirthDate;

        public String getAddress ()
        {
            return address;
        }

        public void setAddress (String address)
        {
            this.address = address;
        }

        public String getGender ()
        {
            return gender;
        }

        public void setGender (String gender)
        {
            this.gender = gender;
        }

        public String getCreated_at ()
        {
            return created_at;
        }

        public void setCreated_at (String created_at)
        {
            this.created_at = created_at;
        }

        public String getPhoto ()
    {
        return photo;
    }

        public void setPhoto (String photo)
        {
            this.photo = photo;
        }

        public String getAdmin ()
    {
        return admin;
    }

        public void setAdmin (String admin)
        {
            this.admin = admin;
        }

        public String getEmail_verified_at ()
    {
        return email_verified_at;
    }

        public void setEmail_verified_at (String email_verified_at)
        {
            this.email_verified_at = email_verified_at;
        }

        public String getUpdated_at ()
        {
            return updated_at;
        }

        public void setUpdated_at (String updated_at)
        {
            this.updated_at = updated_at;
        }

        public int getGroup_id ()
    {
        return group_id;
    }

        public void setGroup_id (int group_id)
        {
            this.group_id = group_id;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public Pivot getPivot ()
        {
            return pivot;
        }

        public void setPivot (Pivot pivot)
        {
            this.pivot = pivot;
        }

        public int getId ()
        {
            return id;
        }

        public void setId (int id)
        {
            this.id = id;
        }

        public String getEmail ()
        {
            return email;
        }

        public void setEmail (String email)
        {
            this.email = email;
        }

        public String getBirthDate ()
        {
            return BirthDate;
        }

        public void setBirthDate (String BirthDate)
        {
            this.BirthDate = BirthDate;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [address = "+address+", gender = "+gender+", created_at = "+created_at+", photo = "+photo+", admin = "+admin+", email_verified_at = "+email_verified_at+", updated_at = "+updated_at+", group_id = "+group_id+", name = "+name+", pivot = "+pivot+", id = "+id+", email = "+email+", BirthDate = "+BirthDate+"]";
        }
        public class Pivot
        {
            private String group_id;

            private String user_id;

            public String getGroup_id ()
            {
                return group_id;
            }

            public void setGroup_id (String group_id)
            {
                this.group_id = group_id;
            }

            public String getUser_id ()
            {
                return user_id;
            }

            public void setUser_id (String user_id)
            {
                this.user_id = user_id;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [group_id = "+group_id+", user_id = "+user_id+"]";
            }
        }




    }
}
