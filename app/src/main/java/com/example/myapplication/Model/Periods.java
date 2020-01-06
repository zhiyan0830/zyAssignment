package com.example.myapplication.Model;

public class Periods {
    private Open open;

    public Open getOpen ()
    {
        return open;
    }

    public void setOpen (Open open)
    {
        this.open = open;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [open = "+open+"]";
    }
}
