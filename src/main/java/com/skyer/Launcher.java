package com.skyer;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;
import org.slf4j.Logger;   
import org.slf4j.LoggerFactory; 
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * web Launcher
 * 
 * @author CrazyPig
 * @since 2016-08-03
 *
 */
public class Launcher {

    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_CONTEXT_PATH = "/JavaWebDemoWithGradle";
    private static final String DEFAULT_APP_CONTEXT_PATH = "src/main/webapp";
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {

        runJettyServer(DEFAULT_PORT, DEFAULT_CONTEXT_PATH);
        
    }

    public static void runJettyServer(int port, String contextPath) {

        Server server = createJettyServer(port, contextPath);
            logger.info("server start :{}","SLF4J");
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Server createJettyServer(int port, String contextPath) {

        Server server = new Server(port);
        server.setStopAtShutdown(true);

        ProtectionDomain protectionDomain = Launcher.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();
        String warFile = location.toExternalForm();

        WebAppContext context = new WebAppContext(warFile, contextPath);
        context.setServer(server);

        String currentDir = new File(location.getPath()).getParent();
        File workDir = new File(currentDir, "work");
        context.setTempDirectory(workDir);

        server.setHandler(context);
        return server;

    }

    public static Server createDevServer(int port, String contextPath) {

        Server server = new Server();
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[] {connector});

        WebAppContext webAppCtx = new WebAppContext(DEFAULT_APP_CONTEXT_PATH, contextPath);
        webAppCtx.setDescriptor(DEFAULT_APP_CONTEXT_PATH + "/WEB-INF/web.xml");
        webAppCtx.setResourceBase(DEFAULT_APP_CONTEXT_PATH);
        webAppCtx.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webAppCtx);

        return server;
    }

}