/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package perlinnoise;

import PerlinNoise.FastNoise;
import PerlinNoise.FastNoise.CellularDistanceFunction;
import PerlinNoise.FastNoise.CellularReturnType;
import PerlinNoise.FastNoise.FractalType;
import PerlinNoise.FastNoise.Interp;
import PerlinNoise.FastNoise.NoiseType;
import PerlinNoise.ImprovedNoise;
import com.badlogic.gdx.math.Vector2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JComboBox;

/**
 *
 * @author fes77
 */
public class PerlinNoise implements MouseWheelListener, ActionListener{
    Pixel[][] grid;
    int frequencyBase = 20;
    JFrame frame;
    FastNoise fn = new FastNoise();
    ImprovedNoise in = new ImprovedNoise();  
    
    
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 300;
    
    float DeepWater = 0.2f;
    float ShallowWater = 0.4f;  
    float Sand = 0.5f;
    float Grass = 0.7f;
    float Forest = 0.8f;
    float Rock = 0.9f;
    float Snow = 1;
    
    private static Color DeepColor = new Color(0, 0, 0.5f, 1);
    private static Color ShallowColor = new Color(25/255f, 25/255f, 150/255f, 1);
    private static Color SandColor = new Color(240 / 255f, 240 / 255f, 64 / 255f, 1);
    private static Color GrassColor = new Color(50 / 255f, 220 / 255f, 20 / 255f, 1);
    private static Color ForestColor = new Color(16 / 255f, 160 / 255f, 0, 1);
    private static Color RockColor = new Color(0.5f, 0.5f, 0.5f, 1);            
    private static Color SnowColor = new Color(1, 1, 1, 1);
    
    float shift = 5;
    float amplitude = 1;

    float frequency32th = 0.03125f;
    float frequency16th = 0.0625f;
    float frequency8th = 0.125f;
    float frequency4th = 0.25f;
    float frequency2nd = 0.5f; 
    float frequency = 1;
    float frequency2 = 2;
    float frequency4 = 4;
    float frequency8 = 8;
    float frequency16 = 16;
    float frequency32 = 32;
    float z = 1;
    

    public PerlinNoise(JFrame frame) 
    {
        fn.SetNoiseType(NoiseType.CubicFractal);
        fn.SetInterp(Interp.Quintic);
        this.frame = frame;
        grid = new Pixel[DEFAULT_HEIGHT][DEFAULT_WIDTH];
        for(int i = 0; i < DEFAULT_WIDTH; i++)
        {
            for(int j = 0; j < DEFAULT_HEIGHT; j++)
            {
//                double noise = SimplexNoise.noise(i * frequency / (float)50, j * frequency/ (float)50)/2 + 0.5;
                double noise = fn.GetNoise(i * frequencyBase, j * frequencyBase);
//                noise += fn.GetNoise(i * frequency, j * frequency);
                
                
                Color color;
                if(noise < DeepWater)
                    color = DeepColor;
                else if(noise < ShallowWater)
                    color = ShallowColor;
                else if(noise < Sand)
                    color = SandColor;
                else if(noise < Grass)
                    color = GrassColor;
                else if(noise < Forest)
                    color = ForestColor;
                else if(noise < Rock)
                    color = RockColor;
                else
                    color = SnowColor;
                
//                grid[i][j] = new Pixel(new Color((int)(255 * (noise/2 + 0.5)), (int)(255 * (noise/2 + 0.5)), (int)(255 * (noise/2 + 0.5))), new Vector2(i, j));
                grid[i][j] = new Pixel(color, new Vector2(i, j));
            }
        }
    }
    
    public void draw(Graphics2D g2d)
    {
        update();
        for(int i = 0; i < DEFAULT_HEIGHT; i++)
        {
            for(int j = 0; j < DEFAULT_WIDTH; j++)
            {
                grid[i][j].draw(g2d);
            }
        }
    }
    
    public void update()
    {
        for(int i = 0; i < DEFAULT_WIDTH; i++)
        {
            for(int j = 0; j < DEFAULT_HEIGHT; j++)
            {
//                double noise = SimplexNoise.noise(i * frequencyBase, j * frequencyBase)/frequency;
//                noise += SimplexNoise.noise(i * frequencyBase * 2, j * frequencyBase * 2)/frequency2;
//                noise += SimplexNoise.noise(i * frequencyBase * 4, j * frequencyBase * 4)/frequency4;
//                noise += SimplexNoise.noise(i * frequencyBase * 8, j * frequencyBase * 8)/frequency8;
//                noise += SimplexNoise.noise(i * frequencyBase * 16, j * frequencyBase * 16)/frequency16;
//                noise += SimplexNoise.noise(i * frequencyBase * 32, j * frequencyBase * 32)/frequency32;
                
                double noise = fn.GetNoise(i * frequencyBase * 0.125f, j * frequencyBase * 0.125f)/frequency8th;
                noise += fn.GetNoise(i * frequencyBase * 0.25f, j * frequencyBase * 0.25f)/frequency4th;
                noise += fn.GetNoise(i * frequencyBase * 0.5f, j * frequencyBase * 0.5f)/frequency2nd;
                noise += fn.GetNoise(i * frequencyBase, j * frequencyBase)/frequency;
                noise += fn.GetNoise(i * frequencyBase * 2, j * frequencyBase * 2)/frequency2;
                noise += fn.GetNoise(i * frequencyBase * 4, j * frequencyBase * 4)/frequency4;
                noise += fn.GetNoise(i * frequencyBase * 8, j * frequencyBase * 8)/frequency8;
                noise += fn.GetNoise(i * frequencyBase * 16, j * frequencyBase * 16)/frequency16;
                noise += fn.GetNoise(i * frequencyBase * 32, j * frequencyBase * 32)/frequency32;
                
//                double noise = fn.GetNoise(i * frequencyBase * 0.03125f, j * frequencyBase * 0.03125f)/frequency32th;
//                noise += fn.GetNoise(i * frequencyBase * 0.0625f, j * frequencyBase * 0.0625f)/frequency16th;
//                noise += fn.GetNoise(i * frequencyBase * 0.125f, j * frequencyBase * 0.125f)/frequency8th;
//                noise += fn.GetNoise(i * frequencyBase * 0.25f, j * frequencyBase * 0.25f)/frequency4th;
//                noise += fn.GetNoise(i * frequencyBase * 0.5f, j * frequencyBase * 0.5f)/frequency2nd;
//                noise += fn.GetNoise(i * frequencyBase, j * frequencyBase)/frequency;
//                noise += fn.GetNoise(i * frequencyBase * 2, j * frequencyBase * 2)/frequency2;
//                noise += fn.GetNoise(i * frequencyBase * 4, j * frequencyBase * 4)/frequency4;
//                noise += fn.GetNoise(i * frequencyBase * 8, j * frequencyBase * 8)/frequency8;
//                noise += fn.GetNoise(i * frequencyBase * 16, j * frequencyBase * 16)/frequency16;
//                noise += fn.GetNoise(i * frequencyBase * 32, j * frequencyBase * 32)/frequency32;
                
//                double noise = ImprovedNoise.noise(i * frequencyBase, j * frequencyBase, z)/frequency;
//                noise += ImprovedNoise.noise(i * frequencyBase * 2, j * frequencyBase * 2, z)/frequency2;
//                noise += ImprovedNoise.noise(i * frequencyBase * 4, j * frequencyBase * 4, z)/frequency4;
//                noise += ImprovedNoise.noise(i * frequencyBase * 8, j * frequencyBase * 8, z)/frequency8;
//                noise += ImprovedNoise.noise(i * frequencyBase * 16, j * frequencyBase * 16, z)/frequency16;
//                noise += ImprovedNoise.noise(i * frequencyBase * 32, j * frequencyBase * 32, z)/frequency32;
                

                
                noise = noise * amplitude / 100f  + shift/100f;
                noise = Math.min(1, Math.max(0, noise));
//                System.out.println(noise);
                Color color;
                if(noise < DeepWater)
                    color = DeepColor;
                else if(noise < ShallowWater)
                    color = ShallowColor;
                else if(noise < Sand)
                    color = SandColor;
                else if(noise < Grass)
                    color = GrassColor;
                else if(noise < Forest)
                    color = ForestColor;
                else if(noise < Rock)
                    color = RockColor;
                else
                    color = SnowColor;
                
//                grid[i][j] = new Pixel(new Color((int)(255 * (noise/2 + 0.5)), (int)(255 * (noise/2 + 0.5)), (int)(255 * (noise/2 + 0.5))), new Vector2(i, j));
                grid[i][j] = new Pixel(color, new Vector2(i, j));
            }
        }
    }
    
    public void setShift(float shift) {
        this.shift = shift;
        System.out.println("Shift " + shift);
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
        System.out.println("Amplitude " + amplitude);
    }
    
    public void setFrequency32th(float frequency32th) {
        this.frequency32th = frequency32th;
        System.out.println("Frequency32th " + frequency32th);
    }
    
    public void setFrequency16th(float frequency16th) {
        this.frequency16th = frequency16th;
        System.out.println("Frequency16th " + frequency16th);
    }
    
    public void setFrequency8th(float frequency8th) {
        this.frequency8th = frequency8th;
        System.out.println("Frequency8th " + frequency8th);
    }
    
    public void setFrequency4th(float frequency4th) {
        this.frequency4th = frequency4th;
        System.out.println("Frequency4th " + frequency4th);
    }
    
    public void setFrequency2nd(float frequency2nd) {
        this.frequency2nd = frequency2nd;
        System.out.println("Frequency2nd " + frequency2nd);
    }
    
    public void setFrequency(float frequency) {
        this.frequency = frequency;
        System.out.println("Frequency " + frequency);
    }

    public void setFrequency2(float frequency2) {
        this.frequency2 = frequency2;
        System.out.println("Frequency2 " + frequency2);
    }

    public void setFrequency4(float frequency4) {
        this.frequency4 = frequency4;
        System.out.println("Frequency4 " + frequency4);
    }

    public void setFrequency8(float frequency8) {
        this.frequency8 = frequency8;
        System.out.println("Frequency8 " + frequency8);
    }

    public void setFrequency16(float frequency16) {
        this.frequency16 = frequency16;
        System.out.println("Frequency16 " + frequency16);
    }

    public void setFrequency32(float frequency32) {
        this.frequency32 = frequency32;
        System.out.println("Frequency32 " + frequency32);
    }
    

    public void setZ(float z) {
        this.z = z;
        System.out.println("Z " + z);
    }
    
    public void setNoiseType(NoiseType noiseType)
    {
        fn.SetNoiseType(noiseType);
        System.out.println("Noise Type Changed " + noiseType);
    }
    
    public void setNoiseType(Interp interp)
    {
        fn.SetInterp(interp);
        System.out.println("Interpolation Changed " + interp);
    }
    
    public void setFractalType(FractalType fractalType)
    {
        fn.SetFractalType(fractalType);
        System.out.println("Fractal Type Changed " + fractalType);
    }
    
    public void setCellularDistanceFunction(CellularDistanceFunction cellularDistanceFunction)
    {
        fn.SetCellularDistanceFunction(cellularDistanceFunction);
        System.out.println("Cellular Distance Function Changed " + cellularDistanceFunction);
    }
    
    public void setCellularReturnType(CellularReturnType cellularReturnType)
    {
        fn.SetCellularReturnType(cellularReturnType);
        System.out.println("Cellular Return Type Changed " + cellularReturnType);
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int n = e.getWheelRotation();
        frequencyBase += n;
        frame.repaint();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        for(int i = 0; i < 100; i++)
//            System.out.println(SimplexNoise.noise(i, i));
        System.setProperty("sun.java2d.opengl", "true");
        
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(PerlinNoise.DEFAULT_WIDTH * Pixel.DEFAULT_SIZE, PerlinNoise.DEFAULT_HEIGHT * Pixel.DEFAULT_SIZE + 50));
        PerlinNoise p = new PerlinNoise(frame);
        frame.addMouseWheelListener(p);
        JPanel panel = new JPanel()
        {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                p.draw(g2d);
            }
        };
        panel.setBackground(Color.GRAY);
        panel.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setVisible(true);

        
        JFrame controlFrame = new JFrame();
        controlFrame.setLayout(new GridLayout(1,1,1,1));
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(400, 600));
        
        JPanel controlePanel = new JPanel();
        controlePanel.setLayout(new GridLayout(1,2,1,1));
        
        JLabel shiftLabel = new JLabel("Shift");
        JLabel amplitudeLabel = new JLabel("Amplitude");
        JLabel frequency32thLabel = new JLabel("Frequency 32th");
        JLabel frequency16thLabel = new JLabel("Frequency 16th");
        JLabel frequency8thLabel = new JLabel("Frequency 8th");
        JLabel frequency4thLabel = new JLabel("Frequency 4th");
        JLabel frequency2ndLabel = new JLabel("Frequency 2nd");
        JLabel frequencyLabel = new JLabel("Frequency 1");
        JLabel frequency2Label = new JLabel("Frequency 2");
        JLabel frequency4Label = new JLabel("Frequency 4");
        JLabel frequency8Label = new JLabel("Frequency 8");
        JLabel frequency16Label = new JLabel("Frequency 16");
        JLabel frequency32Label = new JLabel("Frequency 32");
        JLabel zLabel = new JLabel("z");
        
        JLabel shiftLabelNum = new JLabel("1");
        JLabel amplitudeLabelNum = new JLabel("1");
        JLabel frequency32thLabelNum = new JLabel("0.03");
        JLabel frequency16thLabelNum = new JLabel("0.06");
        JLabel frequency8thLabelNum = new JLabel("0.13");
        JLabel frequency4thLabelNum = new JLabel("0.25");
        JLabel frequency2ndLabelNum = new JLabel("0.5");
        JLabel frequencyLabelNum = new JLabel("1");
        JLabel frequency2LabelNum = new JLabel("2");
        JLabel frequency4LabelNum = new JLabel("4");
        JLabel frequency8LabelNum = new JLabel("8");
        JLabel frequency16LabelNum = new JLabel("16");
        JLabel frequency32LabelNum = new JLabel("32");
        JLabel zLabelNum = new JLabel("1");
        
        float diviserConst = 100f;
        int sliderSize = 10000;
        int tickSpacing = 5000;
        JSlider shiftSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 100)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                shiftLabelNum.setText("" + value/diviserConst);
                p.setShift(value/diviserConst);
                frame.repaint();
            }
        };
        
        shiftSlider.setMajorTickSpacing(tickSpacing);
        shiftSlider.setPaintTicks(true);
        shiftSlider.setPaintLabels(true);
        
        JSlider amplitudeSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, 10000, 100)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                amplitudeLabelNum.setText("" + value/diviserConst);
                p.setAmplitude(value/diviserConst);
                frame.repaint();
            }
        };
        
        amplitudeSlider.setMajorTickSpacing(tickSpacing);
        amplitudeSlider.setPaintTicks(true);
        amplitudeSlider.setPaintLabels(true);
        
        JSlider frequency32thSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 3)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency8thLabelNum.setText("" + value/diviserConst);
                p.setFrequency8th(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency32thSlider.setMajorTickSpacing(tickSpacing);
        frequency32thSlider.setPaintTicks(true);
        frequency32thSlider.setPaintLabels(true);
        
        JSlider frequency16thSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 6)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency8thLabelNum.setText("" + value/diviserConst);
                p.setFrequency8th(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency16thSlider.setMajorTickSpacing(tickSpacing);
        frequency16thSlider.setPaintTicks(true);
        frequency16thSlider.setPaintLabels(true);
        
        JSlider frequency8thSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 13)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency8thLabelNum.setText("" + value/diviserConst);
                p.setFrequency8th(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency8thSlider.setMajorTickSpacing(tickSpacing);
        frequency8thSlider.setPaintTicks(true);
        frequency8thSlider.setPaintLabels(true);
        
        JSlider frequency4thSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 25)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency4thLabelNum.setText("" + value/diviserConst);
                p.setFrequency4th(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency4thSlider.setMajorTickSpacing(tickSpacing);
        frequency4thSlider.setPaintTicks(true);
        frequency4thSlider.setPaintLabels(true);
        
        JSlider frequency2ndSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 50)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency2ndLabelNum.setText("" + value/diviserConst);
                p.setFrequency2nd(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency2ndSlider.setMajorTickSpacing(tickSpacing);
        frequency2ndSlider.setPaintTicks(true);
        frequency2ndSlider.setPaintLabels(true);
        
        JSlider frequencySlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 100)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequencyLabelNum.setText("" + value/diviserConst);
                p.setFrequency(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequencySlider.setMajorTickSpacing(tickSpacing);
        frequencySlider.setPaintTicks(true);
        frequencySlider.setPaintLabels(true);
        
        JSlider frequency2Slider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 200)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency2LabelNum.setText("" + value/diviserConst);
                p.setFrequency2(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency2Slider.setMajorTickSpacing(tickSpacing);
        frequency2Slider.setPaintTicks(true);
        frequency2Slider.setPaintLabels(true);
                
        JSlider frequency4Slider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 400)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency4LabelNum.setText("" + value/diviserConst);
                p.setFrequency4(value/diviserConst);
                frame.repaint();
            }
        };
        
        frequency4Slider.setMajorTickSpacing(tickSpacing);
        frequency4Slider.setPaintTicks(true);
        frequency4Slider.setPaintLabels(true);
        
        JSlider frequency8Slider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 800)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency8LabelNum.setText("" + value/diviserConst);
                p.setFrequency8(value/diviserConst);
                frame.repaint();
            }
        };
        frequency8Slider.setMajorTickSpacing(tickSpacing);
        frequency8Slider.setPaintTicks(true);
        frequency8Slider.setPaintLabels(true);
        
        JSlider frequency16Slider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 1600)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency16LabelNum.setText("" + value/diviserConst);
                p.setFrequency16(value/diviserConst);
                frame.repaint();
            }
        };
        frequency16Slider.setMajorTickSpacing(tickSpacing);
        frequency16Slider.setPaintTicks(true);
        frequency16Slider.setPaintLabels(true);
        
        JSlider frequency32Slider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 3200)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                frequency32LabelNum.setText("" + value/diviserConst);
                p.setFrequency32(value/diviserConst);
                frame.repaint();
            }
        };
        frequency32Slider.setMajorTickSpacing(tickSpacing);
        frequency32Slider.setPaintTicks(true);
        frequency32Slider.setPaintLabels(true);
        
        JSlider zSlider = new JSlider(JSlider.HORIZONTAL,
                                      1, sliderSize, 100)
        {
            public void setValue(int value)
            {
                super.setValue(value);
                zLabelNum.setText("" + value/diviserConst);
                p.setZ(value/diviserConst);
                frame.repaint();
            }
        };
        
//        frequency4Slider.
        HorizontalPanel shiftPanel = new HorizontalPanel();
        shiftPanel.add(shiftLabel);
        shiftPanel.add(shiftSlider);
        shiftPanel.add(shiftLabelNum);
        HorizontalPanel amplitudePanel = new HorizontalPanel();
        amplitudePanel.add(amplitudeLabel);
        amplitudePanel.add(amplitudeSlider);
        amplitudePanel.add(amplitudeLabelNum);
        HorizontalPanel frequency32thPanel = new HorizontalPanel();
        frequency32thPanel.add(frequency32thLabel);
        frequency32thPanel.add(frequency32thSlider);
        frequency32thPanel.add(frequency32thLabelNum);
        HorizontalPanel frequency16thPanel = new HorizontalPanel();
        frequency16thPanel.add(frequency16thLabel);
        frequency16thPanel.add(frequency16thSlider);
        frequency16thPanel.add(frequency16thLabelNum);
        HorizontalPanel frequency8thPanel = new HorizontalPanel();
        frequency8thPanel.add(frequency8thLabel);
        frequency8thPanel.add(frequency8thSlider);
        frequency8thPanel.add(frequency8thLabelNum);
        HorizontalPanel frequency4thPanel = new HorizontalPanel();
        frequency4thPanel.add(frequency4thLabel);
        frequency4thPanel.add(frequency4thSlider);
        frequency4thPanel.add(frequency4thLabelNum);
        HorizontalPanel frequency2ndPanel = new HorizontalPanel();
        frequency2ndPanel.add(frequency2ndLabel);
        frequency2ndPanel.add(frequency2ndSlider);
        frequency2ndPanel.add(frequency2ndLabelNum);
        HorizontalPanel frequencyPanel = new HorizontalPanel();
        frequencyPanel.add(frequencyLabel);
        frequencyPanel.add(frequencySlider);
        frequencyPanel.add(frequencyLabelNum);
        HorizontalPanel frequency2Panel = new HorizontalPanel();
        frequency2Panel.add(frequency2Label);
        frequency2Panel.add(frequency2Slider);
        frequency2Panel.add(frequency2LabelNum);
        HorizontalPanel frequency4Panel = new HorizontalPanel();
        frequency4Panel.add(frequency4Label);
        frequency4Panel.add(frequency4Slider);
        frequency4Panel.add(frequency4LabelNum);
        HorizontalPanel frequency8Panel = new HorizontalPanel();
        frequency8Panel.add(frequency8Label);
        frequency8Panel.add(frequency8Slider);
        frequency8Panel.add(frequency8LabelNum);
        HorizontalPanel frequency16Panel = new HorizontalPanel();
        frequency16Panel.add(frequency16Label);
        frequency16Panel.add(frequency16Slider);
        frequency16Panel.add(frequency16LabelNum);
        HorizontalPanel frequency32Panel = new HorizontalPanel();
        frequency32Panel.add(frequency32Label);
        frequency32Panel.add(frequency32Slider);
        frequency32Panel.add(frequency32LabelNum);
        HorizontalPanel zPanel = new HorizontalPanel();
        zPanel.add(zLabel);
        zPanel.add(zSlider);
        zPanel.add(zLabelNum);
        
        VerticalPanel vpParam = new VerticalPanel();
        
        vpParam.add(shiftPanel);
        vpParam.add(amplitudePanel);
        vpParam.add(frequency32thPanel);
        vpParam.add(frequency16thPanel);
        vpParam.add(frequency8thPanel);
        vpParam.add(frequency4thPanel);
        vpParam.add(frequency2ndPanel);
        vpParam.add(frequencyPanel);
        vpParam.add(frequency2Panel);
        vpParam.add(frequency4Panel);
        vpParam.add(frequency8Panel);
        vpParam.add(frequency16Panel);
        vpParam.add(frequency32Panel);
        vpParam.add(zPanel);
        
        
        JLabel noiseTypeLabel = new JLabel("Noise Type");
        JComboBox noiseTypeCmb = new JComboBox(new NoiseType[]{NoiseType.Value, NoiseType.ValueFractal, NoiseType.Perlin, NoiseType.PerlinFractal, NoiseType.Simplex, NoiseType.SimplexFractal, NoiseType.Cellular, NoiseType.WhiteNoise, NoiseType.Cubic, NoiseType.CubicFractal})
                {
                    public void setSelectedItem(Object item)
                    {
                        super.setSelectedItem(item);
                        p.setNoiseType((NoiseType)item);
                        frame.repaint();
                    }
                };
        
        JLabel interpLabel = new JLabel("Interpolation");
        JComboBox interpCmb = new JComboBox(new Interp[]{Interp.Linear, Interp.Hermite, Interp.Quintic})
                {
                    public void setSelectedItem(Object item)
                    {
                        super.setSelectedItem(item);
                        p.setNoiseType((Interp)item);
                        frame.repaint();
                    }
                };
        
        JLabel fractalTypeLabel = new JLabel("FractalType");
        JComboBox fractalTypeCmb = new JComboBox(new FractalType[]{FractalType.FBM, FractalType.Billow, FractalType.RigidMulti})
                {
                    public void setSelectedItem(Object item)
                    {
                        super.setSelectedItem(item);
                        p.setFractalType((FractalType)item);
                        frame.repaint();
                    }
                };
        
        JLabel cellularDistanceFunctionLabel = new JLabel("CellularDistanceFunction");
        JComboBox cellularDistanceFunctionCmb = new JComboBox(new CellularDistanceFunction[]{CellularDistanceFunction.Euclidean, CellularDistanceFunction.Manhattan, CellularDistanceFunction.Natural})
                {
                    public void setSelectedItem(Object item)
                    {
                        super.setSelectedItem(item);
                        p.setCellularDistanceFunction((CellularDistanceFunction)item);
                        frame.repaint();
                    }
                };
        
        JLabel cellularReturnTypeLabel = new JLabel("CellularReturnType");
        JComboBox cellularReturnTypeCmb = new JComboBox(new CellularReturnType[]{CellularReturnType.CellValue, CellularReturnType.NoiseLookup, CellularReturnType.Distance, CellularReturnType.Distance2, CellularReturnType.Distance2Add, CellularReturnType.Distance2Sub, CellularReturnType.Distance2Mul, CellularReturnType.Distance2Div})
                {
                    public void setSelectedItem(Object item)
                    {
                        super.setSelectedItem(item);
                        p.setCellularReturnType((CellularReturnType)item);
                        frame.repaint();
                    }
                };
        
        
        
        HorizontalPanel noiseTypeLabelPanel = new HorizontalPanel();
        noiseTypeLabelPanel.add(noiseTypeLabel);
        noiseTypeLabelPanel.add(noiseTypeCmb);
        HorizontalPanel interpPanel = new HorizontalPanel();
        interpPanel.add(interpLabel);
        interpPanel.add(interpCmb);
        HorizontalPanel fractalTypePanel = new HorizontalPanel();
        fractalTypePanel.add(fractalTypeLabel);
        fractalTypePanel.add(fractalTypeCmb);
        HorizontalPanel cellularDistanceFunctionPanel = new HorizontalPanel();
        cellularDistanceFunctionPanel.add(cellularDistanceFunctionLabel);
        cellularDistanceFunctionPanel.add(cellularDistanceFunctionCmb);
        HorizontalPanel cellularReturnTypePanel = new HorizontalPanel();
        cellularReturnTypePanel.add(cellularReturnTypeLabel);
        cellularReturnTypePanel.add(cellularReturnTypeCmb);
        
        
        
        
        VerticalPanel vpEnum = new VerticalPanel();
        vpEnum.add(noiseTypeLabelPanel);
        vpEnum.add(interpPanel);
        vpEnum.add(fractalTypePanel);
        vpEnum.add(cellularDistanceFunctionPanel);
        vpEnum.add(cellularReturnTypePanel);
        
        
        
        controlePanel.add(vpParam);
        controlePanel.add(vpEnum);
        controlFrame.add(controlePanel);
        controlFrame.setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if(frequency2Slider )
    }

    
    
}
