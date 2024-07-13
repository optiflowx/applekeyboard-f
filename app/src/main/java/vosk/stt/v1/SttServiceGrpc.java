package vosk.stt.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.65.0)",
    comments = "Source: stt_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SttServiceGrpc {

  private SttServiceGrpc() {}

  public static final String SERVICE_NAME = "vosk.stt.v1.SttService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest,
      vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse> getStreamingRecognizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamingRecognize",
      requestType = vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest.class,
      responseType = vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest,
      vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse> getStreamingRecognizeMethod() {
    io.grpc.MethodDescriptor<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest, vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse> getStreamingRecognizeMethod;
    if ((getStreamingRecognizeMethod = SttServiceGrpc.getStreamingRecognizeMethod) == null) {
      synchronized (SttServiceGrpc.class) {
        if ((getStreamingRecognizeMethod = SttServiceGrpc.getStreamingRecognizeMethod) == null) {
          SttServiceGrpc.getStreamingRecognizeMethod = getStreamingRecognizeMethod =
              io.grpc.MethodDescriptor.<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest, vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamingRecognize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse.getDefaultInstance()))
              .build();
        }
      }
    }
    return getStreamingRecognizeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SttServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SttServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SttServiceStub>() {
        @Override
        public SttServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SttServiceStub(channel, callOptions);
        }
      };
    return SttServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SttServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SttServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SttServiceBlockingStub>() {
        @Override
        public SttServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SttServiceBlockingStub(channel, callOptions);
        }
      };
    return SttServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SttServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SttServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SttServiceFutureStub>() {
        @Override
        public SttServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SttServiceFutureStub(channel, callOptions);
        }
      };
    return SttServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SttServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest> streamingRecognize(
        io.grpc.stub.StreamObserver<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamingRecognizeMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStreamingRecognizeMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest,
                vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse>(
                  this, METHODID_STREAMING_RECOGNIZE)))
          .build();
    }
  }

  /**
   */
  public static final class SttServiceStub extends io.grpc.stub.AbstractAsyncStub<SttServiceStub> {
    private SttServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SttServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SttServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionRequest> streamingRecognize(
        io.grpc.stub.StreamObserver<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getStreamingRecognizeMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SttServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SttServiceBlockingStub> {
    private SttServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SttServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SttServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class SttServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SttServiceFutureStub> {
    private SttServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected SttServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SttServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_STREAMING_RECOGNIZE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SttServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SttServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAMING_RECOGNIZE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamingRecognize(
              (io.grpc.stub.StreamObserver<vosk.stt.v1.SttServiceOuterClass.StreamingRecognitionResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SttServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getStreamingRecognizeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
