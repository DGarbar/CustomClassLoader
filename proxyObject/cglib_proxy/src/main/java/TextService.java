public class TextService  {

  public String staticText() {
    return "server.port = ${port}";
  }

  public String variable(String variable) {
    return variable;
  }

  public String exception(String text) throws RuntimeException {
    throw new MyCustomException();
  }
}
