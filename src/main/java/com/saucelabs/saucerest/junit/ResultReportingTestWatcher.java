package com.saucelabs.saucerest.junit;

import com.saucelabs.saucerest.SauceREST;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.IOException;

public class ResultReportingTestWatcher extends TestWatcher{

    private final SessionIdProvider sessionIdProvider;
    private final SauceREST sauceREST;

    public ResultReportingTestWatcher(SessionIdProvider sessionIdProvider, final String username, final String accessKey) {
        this.sessionIdProvider = sessionIdProvider;
        sauceREST = new SauceREST(username, accessKey);
    }

    protected void succeeded(Description description) {
        try {
            sauceREST.jobPassed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    protected void failed(Throwable e, Description description) {
        try {
            sauceREST.jobFailed(sessionIdProvider.getSessionId());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
