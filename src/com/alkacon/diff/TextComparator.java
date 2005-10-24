/*
 * File   : $Source: /alkacon/cvs/AlkaconDiff/src/com/alkacon/diff/TextComparator.java,v $
 * Date   : $Date: 2005/10/20 07:32:38 $
 * Version: $Revision: 1.1 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.alkacon.diff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.eclipse.compare.rangedifferencer.IRangeComparator;

/**
 * Comparator for comparing text on a line-by-line basis.
 */
class TextComparator implements IRangeComparator {

    private String[] m_lines;

    /**
     * @param text the input text as one big string (thus containing newlines)
     */
    public TextComparator(String text) {

        BufferedReader reader = new BufferedReader(new StringReader(text));

        ArrayList lines = new ArrayList(50);
        try {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected: got exception while reading from String object.", e);
        }
        m_lines = (String[])lines.toArray(new String[lines.size()]);
    }

    /**
     * Returns the line at the given index, or the empty String if no line is available at the index.<p>
     * 
     * @param index the index to use
     * @return the line at the given index, or the empty String if no line is available at the index
     */
    public String getLine(int index) {

        return index < m_lines.length ? m_lines[index] : "";
    }

    /**
     * @see org.eclipse.compare.rangedifferencer.IRangeComparator#getRangeCount()
     */
    public int getRangeCount() {

        return m_lines.length;
    }

    /**
     * @see org.eclipse.compare.rangedifferencer.IRangeComparator#rangesEqual(int, org.eclipse.compare.rangedifferencer.IRangeComparator, int)
     */
    public boolean rangesEqual(int thisIndex, IRangeComparator other, int otherIndex) {

        String thisLine = getLine(thisIndex);
        String otherLine = ((TextComparator)other).getLine(otherIndex);
        return thisLine.equals(otherLine);
    }

    /**
     * @see org.eclipse.compare.rangedifferencer.IRangeComparator#skipRangeComparison(int, int, org.eclipse.compare.rangedifferencer.IRangeComparator)
     */
    public boolean skipRangeComparison(int length, int maxLength, IRangeComparator other) {

        return false;
    }
}