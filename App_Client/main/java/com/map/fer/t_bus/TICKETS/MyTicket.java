package com.map.fer.t_bus.TICKETS;



public class MyTicket {

 double Amount ,AmountTotal;
 int nbrLine;
 String Date ,Time ,nameLine ;




 public int getNbrLine() {
  return nbrLine;
 }

 public void setNbrLine(int nbrLine) {
  this.nbrLine = nbrLine;
 }



 public MyTicket() {
 }

 public String getNameLine() {
  return nameLine;
 }

 public void setNameLine(String nameLine) {
  this.nameLine = nameLine;
 }

 public double getAmount() {
  return Amount;
 }

 public void setAmount(double amount) {
  Amount = amount;
 }

 public double getAmountTotal() {
  return AmountTotal;
 }

 public void setAmountTotal(double amountTotal) {
  AmountTotal = amountTotal;
 }



 public String getDate() {
  return Date;
 }

 public void setDate(String date) {
  Date = date;
 }

 public String getTime() {
  return Time;
 }

 public void setTime(String time) {
  Time = time;
 }
}
