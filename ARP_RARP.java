package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class NetworkDevice {
    private String ipAddress;
    private String macAddress;

    public NetworkDevice(String ipAddress, String macAddress) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }
}

class ProtocolHandler {
    private Map<String, NetworkDevice> arpTable;
    private Map<String, NetworkDevice> rarpTable;

    public ProtocolHandler() {
        arpTable = new HashMap<>();
        rarpTable = new HashMap<>();
    }

    public void addToTables(NetworkDevice device) {
        arpTable.put(device.getIpAddress(), device);
        rarpTable.put(device.getMacAddress(), device);
    }

    public String handleRequest(int protocol, String address) {
        switch (protocol) {
            case 1:
                NetworkDevice arpDevice = arpTable.get(address);
                if (arpDevice != null) {
                    return "MAC address for IP " + address + " is " + arpDevice.getMacAddress();
                } else {
                    return "No MAC address found for IP " + address;
                }

            case 2:
                NetworkDevice rarpDevice = rarpTable.get(address);
                if (rarpDevice != null) {
                    return "IP address for MAC " + address + " is " + rarpDevice.getIpAddress();
                } else {
                    return "No IP address found for MAC " + address;
                }

            default:
                return "Invalid protocol selection. Please choose 'arp' or 'rarp'.";
        }
    }
}

public class ARP_RARP {
    public static void main(String[] args) {
        // Creating network devices
        NetworkDevice device1 = new NetworkDevice("192.168.1.1", "00:1A:2B:3C:4D:5E");
        NetworkDevice device2 = new NetworkDevice("192.168.1.2", "10:11:12:13:14:15");

        // Creating protocol handler instance
        ProtocolHandler protocolHandler = new ProtocolHandler();

        // Adding devices to both ARP and RARP tables
        protocolHandler.addToTables(device1);
        protocolHandler.addToTables(device2);

        // User input for protocol selection and address to resolve
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter protocol 1 for arp and 2 for rarp): ");
        int protocol = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter address to resolve: ");
        String address = scanner.nextLine();

        // Handling the request
        String result = protocolHandler.handleRequest(protocol, address);
        System.out.println(result);
        
        scanner.close();
    }
}
