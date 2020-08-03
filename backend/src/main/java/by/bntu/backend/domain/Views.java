package by.bntu.backend.domain;

public final class Views {

    public interface Id {}

    public interface IdName extends Id {}

    public interface FullComment extends IdName {}

    public interface FullTask extends IdName {}

    public interface FullProject extends IdName {}

    public interface FullProfile extends IdName {}
}
