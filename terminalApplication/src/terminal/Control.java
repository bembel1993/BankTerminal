package terminal;

import java.io.IOException;

interface Control {
    boolean Valid(String ch);

    void caseMenu(String enter) throws IOException;
}
