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
package MinTFramework.Network.Protocol.UDP;

import MinTFramework.MinT;
import MinTFramework.Util.Benchmarks.Performance;
import java.io.IOException;
import java.nio.channels.DatagramChannel;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public class UDPSendThread extends Thread{
    private DatagramChannel datachannel;
    private MinT parent;
    private Performance bench = null;
    public UDPSendThread(Runnable r, DatagramChannel datachannel, String name){
        super(r,name);
        this.datachannel = datachannel;
        
        parent = MinT.getInstance();
        if(parent.getBenchmark().isBenchMode()){
            bench = new Performance("SendAdaptor");
            parent.getBenchmark().addPerformance(UDP.UDP_Thread_Pools.UDP_SENDER.toString(), bench);
        }
    }
    
    public DatagramChannel getDataChannel(){
        return datachannel;
    }
    
    public String getPort(){
        try {
            return datachannel.getLocalAddress().toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public Performance getBench(){
        return bench;
    }
    
    @Override
    public void finalize() throws Throwable{
        super.finalize();
        System.out.println("end of thread Sender-");
    }
}
