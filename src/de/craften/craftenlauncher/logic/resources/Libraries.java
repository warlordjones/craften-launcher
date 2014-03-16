/**
 * CraftenLauncher is an alternative Launcher for Minecraft developed by Mojang.
 * Copyright (C) 2013  Johannes "redbeard" Busch, Sascha "saschb2b" Becker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Declares the versions used library
 *
 * @author saschb2b
 */
package de.craften.craftenlauncher.logic.resources;

import java.util.ArrayList;

public class Libraries {
    private ArrayList<LibEntry> mLiblist = new ArrayList<LibEntry>();

    public Libraries() {
    }

    public void add(LibEntry entry){
        mLiblist.add(entry);
    }

    public ArrayList<LibEntry> get(){
        return mLiblist;
    }
}
