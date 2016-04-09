package com.ginema.crypto.exception;

public class GinemaCryptoException extends RuntimeException {



  private final Throwable throwable;
  private final Type type;

  private static final long serialVersionUID = 7196490748368108184L;

  public static enum Type {
    ENCRYPTION_ERROR, DECRYPTION_ERROR, IO_ERROR,


  };

  public GinemaCryptoException(Throwable throwable, Type type) {
    this.type = type;
    this.throwable = throwable;
  }

  /**
   * @return the throwable
   */
  public Throwable getThrowable() {
    return throwable;
  }

  /**
   * @return the cause
   */
  public Type getType() {
    return type;
  }


}
