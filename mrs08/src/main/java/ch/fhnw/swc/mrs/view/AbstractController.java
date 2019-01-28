package ch.fhnw.swc.mrs.view;

import ch.fhnw.swc.mrs.api.MRSServices;

public abstract class AbstractController {

    private MRSServices backend;

    /**
     * @param aBackend Set the back-end component of this application.
     */
    public void setBackend(MRSServices aBackend) {
        if (aBackend == null) {
            throw new IllegalArgumentException("backend must not be null.");
        }
        backend = aBackend;
    }

    /**
     * @return the back-end component of this application.
     */
    protected MRSServices getBackend() {
        return backend;
    }

    /**
     * Reload this controllers view with new data from the back-end.
     */
    public abstract void reload();

}
