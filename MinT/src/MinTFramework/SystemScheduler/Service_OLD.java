/*
 * Copyright (C) 2015 Software&System Lab. Kangwon National University.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package MinTFramework.SystemScheduler;

import MinTFramework.MinT;
import MinTFramework.ThreadsPool.PoolWorkerThread;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public abstract class Service_OLD {
    private int id;
    private int prior;
    private boolean isInterrupted = false;
    protected final MinT frame;
    private PoolWorkerThread parentThread;
    /***
     * Service Constructor
     * Deafult id=0, prior = 0;
     * @param _frame 
     */
    public Service_OLD(MinT _frame) {
        id = ScheduleWorkerThread.NOT_WORKING_THREAD_SERVICE_ID;
        prior = 0;
        frame = _frame;
        isInterrupted = false;
    }
    
    public Service_OLD(){
        this(null);
    }
    
    /**
     * @deprecated
     * Use Main Thread Method
     * set Service Prior
        NOT USED UNTIL NOW
     * @param pri 
     */
    public void setPrior(int pri){
        this.prior = pri;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    /**
     * @deprecated 
     * Use Main Thread Method
     * @return 
     */
    public int getPrior() {
        return this.prior;
    }

    public int getID() {
        return this.id;
    }
    
    public void serviceInterrupt(){
            isInterrupted = true;
    }
    
    protected boolean isServiceInterrupted(){
        return isInterrupted;
    }
    /**
     * Scheduler execute this method
     * App develper must 
     */
    abstract public void execute();

    void setParentThread(ScheduleWorkerThread aThis) {
        parentThread = aThis;
    }
}
