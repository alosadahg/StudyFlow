package com.example.studyflow;

public class QuestionAnswer {

    public static String[] question = {
            "What does SQL stand for?",
            "Which statement is used to retrieve data from a SQL database?",
            "Which SQL clause is used to filter records in a SELECT statement?",
            "Which data manipulation statement is used to modify existing data in a SQL database?",
            "Which SQL statement is used to create a new table in a database?"};

    public static String choices[][] = {
            {"Structured Query Language","System Query Logic","Sequential Query Language","Syntax Query Library"},
            {"SELECT","UPDATE","INSERT","DELETE"},
            {"FROM","WHERE","ORDER BY","GROUP BY"},
            {"ALTER","DELETE","UPDATE","INSERT"},
            {"SELECT","CREATE","INSERT","ALTER"}
    };

    public static String correctAnswers[] = {
            "Structured Query Language",
            "SELECT",
            "WHERE",
            "UPDATE",
            "CREATE"
    };

}
