package com.logicline.sample.agent.exceptions;

public class AgentRuntimeException extends Exception {

  private static final long serialVersionUID = 1L;

  public AgentRuntimeException(final String string) {
    super(string);
  }

  public AgentRuntimeException(final String string, final Throwable cause) {
    super(string, cause);
  }
}
