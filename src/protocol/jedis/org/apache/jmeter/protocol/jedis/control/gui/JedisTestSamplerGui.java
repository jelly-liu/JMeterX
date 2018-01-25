package org.apache.jmeter.protocol.jedis.control.gui;

import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jedis.config.gui.JedisConfigGui;
import org.apache.jmeter.protocol.jedis.sampler.JedisSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import java.awt.*;

public class JedisTestSamplerGui extends AbstractSamplerGui {
    private static final long serialVersionUID = 240L;

    private JedisConfigGui jedisConfigGui;

    public JedisTestSamplerGui() {
        init();
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        jedisConfigGui.configure(element);
    }

    @Override
    public TestElement createTestElement() {
        JedisSampler sampler = new JedisSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    /**
     * Modifies a given TestElement to mirror the data in the gui components.
     *
     * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
     */
    @Override
    public void modifyTestElement(TestElement sampler) {
        sampler.clear();
        jedisConfigGui.modifyTestElement(sampler);
        super.configureTestElement(sampler);
    }

    /**
     * Implements JMeterGUIComponent.clearGui
     */
    @Override
    public void clearGui() {
        super.clearGui();

        jedisConfigGui.clearGui();
    }

    @Override
    public String getLabelResource() {
        return "jedis_test_sampler_config"; // $NON-NLS-1$
    }

    private void init() { // WARNING: called from ctor so must not be overridden (i.e. must be private or final)
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);

        VerticalPanel mainPanel = new VerticalPanel();

        jedisConfigGui = new JedisConfigGui();
        mainPanel.add(jedisConfigGui);

        add(mainPanel, BorderLayout.CENTER);
    }
}
