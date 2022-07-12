package com.yzm.designmode.R状态模式State;

/**
 * 状态模式
 */
public class StateClient {
    public static void main(String[] args) {
        //第一间房
        Room room = new Room();
        room.bookRoom();    //预订
        room.checkInRoom();   //入住
        room.bookRoom();    //预订
        System.out.println(room);
        System.out.println("---------------------------");

        //第二间房
        Room room2 = new Room();
        room2.checkInRoom(); //入住
        room2.bookRoom(); //预订
        room2.checkOutRoom(); //退房
        room2.bookRoom(); //预订
        System.out.println(room2);
        System.out.println("---------------------------");

        //第三间房
        Room room3 = new Room();
        room3.unsubscribeRoom(); //退订
        room3.checkOutRoom(); //退房
        room3.checkInRoom(); //入住
        room3.bookRoom(); //预订
        System.out.println(room3);
    }
}

interface IState {
    //预订房间
    void bookRoom();

    //退订房间
    void unsubscribeRoom();

    //入住
    void checkInRoom();

    //退房
    void checkOutRoom();
}

class Room {

    IState freeTimeState;    //空闲状态
    IState checkInState;     //入住状态
    IState bookedState;      //预订状态

    IState state;

    public Room() {
        freeTimeState = new FreeTimeState(this);
        checkInState = new CheckInState(this);
        bookedState = new BookedState(this);

        state = freeTimeState;  //初始状态为空闲
    }

    public void bookRoom() {
        state.bookRoom();
    }

    public void unsubscribeRoom() {
        state.unsubscribeRoom();
    }

    public void checkInRoom() {
        state.checkInRoom();
    }

    public void checkOutRoom() {
        state.checkOutRoom();
    }

    @Override
    public String toString() {
        return "该房间的状态是:" + getState().getClass().getName();
    }

    //getter和setter方法
    public IState getFreeTimeState() {
        return freeTimeState;
    }

    public void setFreeTimeState(IState freeTimeState) {
        this.freeTimeState = freeTimeState;
    }

    public IState getCheckInState() {
        return checkInState;
    }

    public void setCheckInState(IState checkInState) {
        this.checkInState = checkInState;
    }

    public IState getBookedState() {
        return bookedState;
    }

    public void setBookedState(IState bookedState) {
        this.bookedState = bookedState;
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }
}

/**
 * @Description: 空闲状态可以预订和入住
 */
class FreeTimeState implements IState {

    Room hotelManagement;

    public FreeTimeState(Room hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @Override
    public void bookRoom() {
        System.out.println("您已经成功预订了...");
        hotelManagement.setState(hotelManagement.getBookedState());   //状态变成已经预订
    }

    @Override
    public void checkInRoom() {
        System.out.println("您已经成功入住了...");
        hotelManagement.setState(hotelManagement.getCheckInState());   //状态变成已经入住
    }

    @Override
    public void unsubscribeRoom() {
        System.out.println("当前房间空闲，可预订或入住...");
    }

    @Override
    public void checkOutRoom() {
        System.out.println("当前房间空闲，可预订或入住...");
    }
}

/**
 * @Description: 预订状态可以入住和退订
 */
class BookedState implements IState {
    Room hotelManagement;

    public BookedState(Room hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @Override
    public void bookRoom() {
        System.out.println("当前房间已预订，可入住或退订...");
    }

    @Override
    public void checkInRoom() {
        System.out.println("您已经成功入住了...");
        hotelManagement.setState(hotelManagement.getCheckInState());         //状态变成入住
    }

    @Override
    public void unsubscribeRoom() {
        System.out.println("退订成功,欢迎下次光临...");
        hotelManagement.setState(hotelManagement.getFreeTimeState());   //变成空闲状态
    }

    @Override
    public void checkOutRoom() {
        System.out.println("当前房间已预订，可入住或退订...");
    }
}

/**
 * @Description: 入住可以退房
 */
class CheckInState implements IState {
    Room hotelManagement;

    public CheckInState(Room hotelManagement) {
        this.hotelManagement = hotelManagement;
    }

    @Override
    public void bookRoom() {
        System.out.println("当前房间已入住，可退房...");
    }

    @Override
    public void checkInRoom() {
        System.out.println("当前房间已入住，可退房...");
    }

    @Override
    public void checkOutRoom() {
        System.out.println("退房成功....");
        hotelManagement.setState(hotelManagement.getFreeTimeState());     //状态变成空闲
    }

    @Override
    public void unsubscribeRoom() {
        System.out.println("当前房间已入住，可退房...");
    }
}