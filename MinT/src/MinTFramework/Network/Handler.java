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
package MinTFramework.Network;

import MinTFramework.*;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public abstract class Handler {
    protected MinT frame;
    public Handler(MinT _frame){
        this.frame = _frame;
    }
    /***
     * Call Packet Handler
     * @param src packet source
     * @param msg pakcet message
     * @param frame MinTFramework
     */
    @Deprecated
    public void callPacketHandleRequest(String src, String msg, MinT frame){};
    /***
     * Call Packet Handler
     * @param src packet source
     * @param msg pakcet message
     */
    abstract public void callPacketHandleRequest(String src, String msg);
    
//    protected void allhadle(String src, String msg){
//        SystemCall(src, msg);
//        callPacketHandleRequest(src, msg);
//    }
//    
//    private void SystemCall(String src, String msg){
//        
//    }
}