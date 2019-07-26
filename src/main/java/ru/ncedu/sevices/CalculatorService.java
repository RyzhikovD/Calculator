package ru.ncedu.sevices;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import ru.ncedu.calculator.CalculatorRequest;
import ru.ncedu.calculator.CalculatorResponse;
import ru.ncedu.calculator.CalculatorServiceGrpc;

public class CalculatorService extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void calculate(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        double answer;
        switch(request.getOperation()) {
            case("*") :
                answer = request.getFirstOperand() * request.getSecondOperand();
                break;
            case("+") :
                answer = request.getFirstOperand() + request.getSecondOperand();
                break;
            case("-") :
                answer = request.getFirstOperand() - request.getSecondOperand();
                break;
            case("/") :
                answer = request.getFirstOperand() / request.getSecondOperand();
                break;
            default:
                responseObserver.onError(new StatusRuntimeException(Status.UNIMPLEMENTED));
                return;
        }
        CalculatorResponse reply = CalculatorResponse.newBuilder()
                .setAnswer(answer)
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}