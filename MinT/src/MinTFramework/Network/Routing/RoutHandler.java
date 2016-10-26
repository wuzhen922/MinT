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
package MinTFramework.Network.Routing;

import MinTFramework.MinT;
import MinTFramework.Network.NetworkManager;
import MinTFramework.Network.PacketDatagram;
import MinTFramework.Network.Resource.ReceiveMessage;
import MinTFramework.Network.Resource.Request;
import MinTFramework.Network.Routing.RoutingProtocol.ROUTING_PHASE;
import MinTFramework.Network.SendMSG;
import MinTFramework.storage.ResourceStorage;
import MinTFramework.storage.datamap.Information;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author soobin Jeon <j.soobin@gmail.com>, chungsan Lee <dj.zlee@gmail.com>,
 * youngtak Han <gksdudxkr@gmail.com>
 */
public class RoutHandler {

    private MinT frame;
    private NetworkManager networkManager;
    private ResourceStorage resStorage;
    private RoutingProtocol rout;
    private ConcurrentHashMap<ROUTING_PHASE, Phase> routingPhase;
    
    public RoutHandler(RoutingProtocol rprotocol) {
        frame = MinT.getInstance();
        networkManager = frame.getNetworkManager();
        resStorage = frame.getResStorage();
        rout = rprotocol;
        routingPhase = rout.phases;
    }

    void receiveHandle(PacketDatagram rv_packet) {
        Request req = new ReceiveMessage(rv_packet.getMsgData(), rv_packet.getSource());
        
        if(rv_packet.getHeader_Code().isRequest())
            requestHandle(rv_packet, req);
        else if(rv_packet.getHeader_Code().isResponse())
            responsehandle(rv_packet, req);
    }

    private void requestHandle(PacketDatagram rv_packet, Request req) {
        Request ret = null;
        Information data = req.getResourcebyName(Request.MSG_ATTR.Routing);
        /**
         * Operate a message according to routing phase
         */
        for(Phase cp : routingPhase.values()){
            if(cp.hasMessage(data.getResourceInt())){
                cp.requestHandle(rv_packet, req);
                break;
            }
        }
        
//        if(isDiscovery(req)){
//            System.out.println("Request out in routing handler");
//            Network cnet = frame.getNetworkManager().getNetwork(rv_packet.getSource().getNetworkType());
//            String redata = resStorage.DiscoverLocalResource(cnet.getProfile()).toJSONString();
//            ret = new SendMessage(null, redata)
//                    .AddAttribute(Request.MSG_ATTR.Routing, null)
//                    .AddAttribute(Request.MSG_ATTR.WellKnown, null);
//        }
        
        if(ret != null){
            networkManager.SEND(new SendMSG(PacketDatagram.HEADER_TYPE.NON, 0
                    , PacketDatagram.HEADER_CODE.CONTENT, rv_packet.getSource(), ret, rv_packet.getMSGID()));
        }
    }

    private void responsehandle(PacketDatagram rv_packet, Request req) {
        Information rdata = req.getResourcebyName(Request.MSG_ATTR.Routing);
        
        for(Phase cp : routingPhase.values()){
            if(cp.hasMessage(rdata.getResourceInt())){
                cp.responseHandle(rv_packet, req);
                break;
            }
        }
        
//        if(isDiscovery(req)){
//            System.out.println("update discovery data in Routing Handler");
//            ResponseData resdata = new ResponseData(rv_packet, req.getResourceData().getResource());
//            resStorage.updateDiscoverData(resdata);
//        }
    }
}