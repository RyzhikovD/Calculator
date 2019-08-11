package ru.ncedu.clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import ru.ncedu.calculator.CalculatorRequest;
import ru.ncedu.calculator.CalculatorResponse;
import ru.ncedu.calculator.CalculatorServiceGrpc;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {
    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);

        int firstOperand;
        int secondOperand;
        String operation;
        Scanner scanner = new Scanner(System.in);
        firstOperand = scanner.nextInt();
        secondOperand = scanner.nextInt();
        operation = scanner.next();

        try {
            CalculatorResponse calculatorResponse = stub.calculate(CalculatorRequest.newBuilder()
                    .setFirstOperand(firstOperand)
                    .setSecondOperand(secondOperand)
                    .setOperation(operation)
                    .build());
//            System.out.println("calculatorResponse.getAnswer() = " + calculatorResponse.getAnswer());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNIMPLEMENTED) {
                logger.log(Level.SEVERE, "Operation not implemented", e);
            } else {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        channel.shutdown();
    }
}