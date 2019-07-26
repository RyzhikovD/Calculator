package ru.ncedu.servers;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import ru.ncedu.sevices.CalculatorService;

import java.io.IOException;

public class NettyGrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        final int port = 8080;
        Server server = NettyServerBuilder
                        .forPort(port)
                        .addService(new CalculatorService())
                        .build();
        server.start();
        server.awaitTermination();
    }
}
