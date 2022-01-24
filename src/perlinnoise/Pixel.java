/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package perlinnoise;

import com.badlogic.gdx.math.Vector2;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author fes77
 */
public class Pixel {
    Color color;
    Vector2 position;
    int size;

    public static int DEFAULT_SIZE = 2;
    public Pixel(Color color, Vector2 position, int size) {
        this.color = color;
        this.position = position;
        this.size = size;
    }
    
    public Pixel(Color color, Vector2 position) {
        this.color = color;
        this.position = position;
        this.size = DEFAULT_SIZE;
    }
    
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(color);
        g2d.fillRect((int)position.x * size, (int)position.y * size, size, size);
    }

    void setColor(Color color) {
        this.color = color;
    }
    
}
