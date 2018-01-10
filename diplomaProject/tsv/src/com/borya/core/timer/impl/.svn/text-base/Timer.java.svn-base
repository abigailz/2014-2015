package com.borya.core.timer.impl;

import java.util.Date;
import java.util.TimerTask;

class Timer extends java.util.Timer{
	
    public Timer() {
        super();
    }

    public Timer(boolean isDaemon) {
        super(isDaemon);
    }

    public Timer(String name) {
        super(name);
    }
    
    public Timer(String name, boolean isDaemon) {
        super(name,isDaemon);
    }

    
    public void schedule(TimerTask task, long delay) {
       super.schedule(task,delay);
    }

    public void schedule(TimerTask task, Date time) {
        super.schedule(task, time);
    }

    public void schedule(TimerTask task, long delay, long period) {
    	super.schedule(task, delay, period);
    }

   
    public void schedule(TimerTask task, Date firstTime, long period) {
    	super.schedule(task, firstTime,period);
    }

    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        super.scheduleAtFixedRate(task, delay, period);
    }

    public void scheduleAtFixedRate(TimerTask task, Date firstTime,
                                    long period) {
        super.scheduleAtFixedRate(task, firstTime, period);
    }

    public void cancel() {
       super.cancel();
    }

     public int purge() {
         return super.purge();
     }
}