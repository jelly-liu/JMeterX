package org.apache.jmeter.protocol.jedis.config.gui;

import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jedis.sampler.JedisOptType;
import org.apache.jmeter.protocol.jedis.sampler.JedisSampler;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JedisPoolConfigGui extends AbstractConfigGui {
    private boolean displayName = true;

    private JTextField server;
    private JTextField port;
    private JTextField connectTimeout;//socket connect timeout
    private JTextField soTimeout;//socket read timeout
    private JTextField maxConnection;
    private JTextField maxIdle;
    private JTextField auth;

    private JTextField key;
    private JTextField value;

    private JRadioButton getJRadioButton;
    private JRadioButton setJRadioButton;
    private JRadioButton deleteJRadioButton;

    public JedisPoolConfigGui() {
        this(true);
    }

    public JedisPoolConfigGui(boolean displayName) {
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

    private JPanel createConnectTimeoutPanel() {
        JLabel label = new JLabel("ConnectTimeout"); //$NON-NLS-1$

        connectTimeout = new JTextField(30);
        label.setLabelFor(connectTimeout);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(connectTimeout, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createSoTimeoutPanel() {
        JLabel label = new JLabel("SoTimeout(ReadTimeout)"); //$NON-NLS-1$

        soTimeout = new JTextField(30);
        label.setLabelFor(soTimeout);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(soTimeout, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createMaxConnectionPanel() {
        JLabel label = new JLabel("MaxConnection"); //$NON-NLS-1$

        maxConnection = new JTextField(30);
        label.setLabelFor(maxConnection);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(maxConnection, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createMaxIdlePanel() {
        JLabel label = new JLabel("MaxIdle"); //$NON-NLS-1$

        maxIdle = new JTextField(30);
        label.setLabelFor(maxIdle);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(maxIdle, BorderLayout.CENTER);
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

    private JPanel createValuePanel() {
        JLabel label = new JLabel("Value"); //$NON-NLS-1$

        value = new JTextField(30);
        label.setLabelFor(value);

        JPanel jPanel = new JPanel(new BorderLayout(5, 0));
        jPanel.add(label, BorderLayout.WEST);
        jPanel.add(value, BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel createGetOrSetPanel(){
        VerticalPanel getOrSetPanel = new VerticalPanel();
        getOrSetPanel.setBorder(BorderFactory.createTitledBorder("Jedis Operation Type"));

        getJRadioButton = new JRadioButton("Get", true);
        setJRadioButton = new JRadioButton("Set");
        deleteJRadioButton = new JRadioButton("Del");

        ButtonGroup group = new ButtonGroup();
        group.add(getJRadioButton);
        group.add(setJRadioButton);
        group.add(deleteJRadioButton);

        HorizontalPanel contentPanel = new HorizontalPanel();
        contentPanel.setBorder(BorderFactory.createEtchedBorder());
        contentPanel.add(getJRadioButton);
        contentPanel.add(setJRadioButton);
        contentPanel.add(deleteJRadioButton);

        getOrSetPanel.add(contentPanel);
        return getOrSetPanel;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        element.setProperty(JedisSampler.SERVER, server.getText());
        element.setProperty(JedisSampler.PORT, port.getText());
        element.setProperty(JedisSampler.CONNECT_TIMEOUT, connectTimeout.getText());
        element.setProperty(JedisSampler.SO_TIMEOUT, soTimeout.getText());
        element.setProperty(JedisSampler.MAX_CONNECTION, maxConnection.getText());
        element.setProperty(JedisSampler.MAX_IDLE, maxIdle.getText());
        element.setProperty(JedisSampler.AUTH, auth.getText());
        element.setProperty(JedisSampler.KEY, key.getText());
        element.setProperty(JedisSampler.VALUE, value.getText());

        if(getJRadioButton.isSelected()){
            element.setProperty(JedisSampler.OPT_TYPE, JedisOptType.GET.name());
        }else if(setJRadioButton.isSelected()){
            element.setProperty(JedisSampler.OPT_TYPE, JedisOptType.SET.name());
        }else if(deleteJRadioButton.isSelected()){
            element.setProperty(JedisSampler.OPT_TYPE, JedisOptType.DEL.name());
        }else{
            throw new RuntimeException("you should select an jedis operation type, get or set or del");
        }
    }

    private void setDefaultValue(){
        this.server.setText("192.168.1.107");
        this.port.setText("7379");
        this.connectTimeout.setText("1000");
        this.soTimeout.setText("1000");
        this.maxConnection.setText("1000");
        this.maxIdle.setText("200");
        this.auth.setText("Ybzh1onzk1az");

        key.setText("${__RandomString(32,abcdefghijklmnopqrstuvwxyz)}");
        value.setText("${__RandomString(128,abcdefghijklmnopqrstuvwxyz)}");
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        if (displayName) {
            setBorder(makeBorder());
            add(makeTitlePanel(), BorderLayout.NORTH);
        }

        // MAIN PANEL
        VerticalPanel mainPanel = new VerticalPanel();

        VerticalPanel pollConfigPanel = new VerticalPanel();
        pollConfigPanel.setBorder(BorderFactory.createTitledBorder("Jedis Pool Config"));
        HorizontalPanel serverPanel = new HorizontalPanel();
        serverPanel.add(createServerPanel(), BorderLayout.CENTER);
        serverPanel.add(createPortPanel(), BorderLayout.EAST);
        pollConfigPanel.add(serverPanel);
        pollConfigPanel.add(createConnectTimeoutPanel());
        pollConfigPanel.add(createSoTimeoutPanel());
        pollConfigPanel.add(createMaxConnectionPanel());
        pollConfigPanel.add(createMaxIdlePanel());
        pollConfigPanel.add(createAuthPanel());
        mainPanel.add(pollConfigPanel);

        mainPanel.add(createGetOrSetPanel());

        VerticalPanel kvPanel = new VerticalPanel();
        kvPanel.setBorder(BorderFactory.createTitledBorder("Key Value Panel"));
        kvPanel.add(createKeyPanel());
        kvPanel.add(createValuePanel());
        mainPanel.add(kvPanel);

        add(mainPanel, BorderLayout.CENTER);

        setDefaultValue();
    }
}
