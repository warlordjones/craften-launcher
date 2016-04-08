/**
 * CraftenLauncher is an alternative Launcher for Minecraft developed by Mojang.
 * Copyright (C) 2014  Johannes "redbeard" Busch, Sascha "saschb2b" Becker
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * MinecraftSkin Class:
 * <p>
 * Displays a Jpanel with a minecraft skin inside
 *
 * @author saschb2b
 * @author leMaik
 */

package de.craften.craftenlauncher.gui.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MinecraftSkin {
    private static final Rectangle
            SKIN_HEAD = new Rectangle(8, 8, 8, 8),
            SKIN_HELMET = new Rectangle(40, 8, 8, 8),
            SKIN_BODY = new Rectangle(20, 20, 8, 12),
            SKIN_RIGHT_LEG = new Rectangle(4, 20, 4, 12),
            SKIN_LEFT_LEG = new Rectangle(20, 52, 4, 12),
            SKIN_RIGHT_ARM = new Rectangle(44, 20, 4, 12),
            SKIN_LEFT_ARM = new Rectangle(36, 52, 4, 12),
            SKIN_BODY_OVERLAY = new Rectangle(20, 36, 8, 12),
            SKIN_RIGHT_LEG_OVERLAY = new Rectangle(4, 36, 4, 12),
            SKIN_LEFT_LEG_OVERLAY = new Rectangle(4, 52, 4, 12),
            SKIN_RIGHT_ARM_OVERLAY = new Rectangle(44, 36, 4, 12),
            SKIN_LEFT_ARM_OVERLAY = new Rectangle(52, 52, 4, 12);

    private MinecraftSkin() {
    }

    private static BufferedImage getSkinPart(BufferedImage skin, Rectangle rect) {
        return skin.getSubimage(rect.x, rect.y, rect.width, rect.height);
    }

    private static BufferedImage getFlippedSkinPart(BufferedImage skin, Rectangle rect) {
        BufferedImage src = getSkinPart(skin, rect);
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-src.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(src, null);
    }

    public static BufferedImage getBodySkin(BufferedImage skin) {
        BufferedImage body = new BufferedImage(16, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics g = body.createGraphics();
        g.drawImage(getSkinPart(skin, SKIN_HEAD), 4, 1, null);
        g.drawImage(getSkinPart(skin, SKIN_BODY), 4, 9, null);
        g.drawImage(getSkinPart(skin, SKIN_RIGHT_ARM), 0, 9, null);
        g.drawImage(getSkinPart(skin, SKIN_RIGHT_LEG), 4, 21, null);

        if (skin.getHeight() == 64) { //1.8 skin
            g.drawImage(getSkinPart(skin, SKIN_LEFT_ARM), 12, 9, null);
            g.drawImage(getSkinPart(skin, SKIN_LEFT_LEG), 8, 21, null);

            //overlays
            g.drawImage(getSkinPart(skin, SKIN_BODY_OVERLAY), 4, 9, null);
            g.drawImage(getSkinPart(skin, SKIN_LEFT_ARM_OVERLAY), 12, 9, null);
            g.drawImage(getSkinPart(skin, SKIN_RIGHT_ARM_OVERLAY), 0, 9, null);
            g.drawImage(getSkinPart(skin, SKIN_LEFT_LEG_OVERLAY), 8, 21, null);
            g.drawImage(getSkinPart(skin, SKIN_RIGHT_LEG_OVERLAY), 4, 21, null);
        } else {
            //draw flipped arm and leg
            g.drawImage(getFlippedSkinPart(skin, SKIN_RIGHT_ARM), 12, 9, null);
            g.drawImage(getFlippedSkinPart(skin, SKIN_RIGHT_LEG), 8, 21, null);
        }

        //helmet last (so that it isn't under the body overlay)
        g.drawImage(getSkinPart(skin, SKIN_HELMET), 3, 0, 10, 10, null);

        return body;
    }
}