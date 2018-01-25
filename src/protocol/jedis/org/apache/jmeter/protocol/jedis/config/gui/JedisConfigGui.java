package org.apache.jmeter.protocol.jedis.config.gui;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jedis.sampler.JedisSampler;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

public class JedisConfigGui extends AbstractConfigGui {
    private boolean displayName = true;

    private JTextField server;
    private JTextField port;
    private JTextField auth;

    private JTextField key;

    public JedisConfigGui() {
        this(true);
    }

    public JedisConfigGui(boolean displayName) {
        this.displayName = displayName;
        init();
    }

    @Override
    public String getLabelResource() {
        return "jedis_config"; // $NON-NLS-1$
    }

    @Override
    public TestElement createTestElement() {
        ConfigTestElement element = new ConfigTestElement();
        modifyTestElement(element);
        return element;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        // Note: the element is a ConfigTestElement, so cannot use FTPSampler access methods
        element.setProperty(JedisSampler.SERVER, server.getText());
        element.setProperty(JedisSampler.PORT, port.getText());
        element.setProperty(JedisSampler.AUTH, auth.getText());
        element.setProperty(JedisSampler.KEY, key.getText());
    }

    private JPanel createServerPanel() {
        JLabel label = new JLabel("Server"); //$NON-NLS-1$

        server = new JTextField(30);
        label.setLabelFor(server);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(server, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createPortPanel() {
        JLabel label = new JLabel("Port"); //$NON-NLS-1$

        port = new JTextField(30);
        label.setLabelFor(port);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(port, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createAuthPanel() {
        JLabel label = new JLabel("Auth"); //$NON-NLS-1$

        auth = new JTextField(30);
        label.setLabelFor(auth);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(auth, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createKeyPanel() {
        JLabel label = new JLabel("Key"); //$NON-NLS-1$

        key = new JTextField(30);
        label.setLabelFor(key);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(key, BorderLayout.CENTER);
        return jPanel;
    }

    private void setDefaultValue(){
        this.server.setText("192.168.1.107");
        this.port.setText("7379");
        this.auth.setText("Ybzh1onzk1az");
        this.key.setText("tom");
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        if (displayName) {
            setBorder(makeBorder());
            add(makeTitlePanel(), BorderLayout.NORTH);
        }

        // MAIN PANEL
        VerticalPanel mainPanel = new VerticalPanel();
        JPanel serverPanel = new HorizontalPanel();
        serverPanel.add(createServerPanel(), BorderLayout.CENTER);
        serverPanel.add(createPortPanel(), BorderLayout.EAST);
        mainPanel.add(serverPanel);
        mainPanel.add(createAuthPanel());
        mainPanel.add(createKeyPanel());

        add(mainPanel, BorderLayout.CENTER);

        setDefaultValue();
    }
}
