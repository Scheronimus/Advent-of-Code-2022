package day16;

import java.util.List;

public class Valve {
    String id;
    int flow;
    List<String> tunnelsTo;
    boolean valveOpen = false;

    public Valve(final String id, final int flow, final List<String> tunnelsTo) {
        super();
        this.id = id;
        this.flow = flow;
        this.tunnelsTo = tunnelsTo;
    }


}
