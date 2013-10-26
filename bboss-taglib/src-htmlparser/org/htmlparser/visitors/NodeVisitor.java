// HTMLParser Library $Name: v1_5 $ - A java-based parser for HTML
// http://sourceforge.org/projects/htmlparser
// Copyright (C) 2004 Somik Raha
//
// Revision Control Information
//
// $Source: /cvsroot/htmlparser/htmlparser/src/org/htmlparser/visitors/NodeVisitor.java,v $
// $Author: derrickoswald $
// $Date: 2005/04/24 17:48:27 $
// $Revision: 1.39 $
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//

package org.htmlparser.visitors;

import org.htmlparser.Remark;
import org.htmlparser.Text;
import org.htmlparser.Tag;

/**
 * The base class for the 'Visitor' pattern.
 * Classes that wish to use <code>visitAllNodesWith()</code> will subclass
 * this class and provide implementations for methods they are interested in
 * processing.<p>
 * The operation of <code>visitAllNodesWith()</code> is to call
 * <code>beginParsing()</code>, then <code>visitXXX()</code> according to the
 * types of nodes encountered in depth-first order and finally
 * <code>finishedParsing()</code>.<p>
 * Typical code to print all the link tags:
 * <pre>
 * import org.htmlparser.Parser;
 * import org.htmlparser.Tag;
 * import org.htmlparser.Text;
 * import org.htmlparser.util.ParserException;
 * import org.htmlparser.visitors.NodeVisitor;
 * 
 * public class MyVisitor extends NodeVisitor
 * {
 *     public MyVisitor ()
 *     {
 *     }
 *
 *     public void visitTag (Tag tag)
 *     {
 *         System.out.println ("\n" + tag.getTagName () + tag.getStartPosition ());
 *     }
 *
 *     public void visitStringNode (Text string)
 *     {
 *         System.out.println (string);
 *     }
 *
 *     public static void main (String[] args) throws ParserException
 *     {
 *         Parser parser = new Parser ("http://cbc.ca");
 *         Visitor visitor = new MyVisitor ();
 *         parser.visitAllNodesWith (visitor);
 *     }
 * }
 * </pre>
 * If you want to handle more than one tag type with the same visitor
 * you will need to check the tag type in the visitTag method. You can
 * do that by either checking the tag name:
 * <pre>
 *     public void visitTag (Tag tag)
 *     {
 *        if (tag.getName ().equals ("BODY"))
 *            ... do something with the BODY tag
 *        else if (tag.getName ().equals ("FRAME"))
 *            ... do something with the FRAME tag
 *    }
 * </pre>
 * or you can use <code>instanceof</code> if all the tags you want to handle
 * have a {@link org.htmlparser.PrototypicalNodeFactory#registerTag registered}
 * tag (i.e. they are generated by the NodeFactory):
 * <pre>
 *     public void visitTag (Tag tag)
 *     {
 *        if (tag instanceof BodyTag)
 *        {
 *            BodyTag body = (BodyTag)tag;
 *            ... do something with body
 *        }
 *        else if (tag instanceof FrameTag)
 *        {
 *            FrameTag frame = (FrameTag)tag;
 *            ... do something with frame
 *        }
 *        else // other specific tags and generic TagNode objects
 *        {
 *        }
 *    }
 */
public abstract class NodeVisitor
{
    private boolean mRecurseChildren;
    private boolean mRecurseSelf;

    /**
     * Creates a node visitor that recurses itself and it's children.
     */
    public NodeVisitor ()
    {
        this (true);
    }
    
    /**
     * Creates a node visitor that recurses itself and it's children
     * only if <code>recurseChildren</code> is <code>true</code>.
     * @param recurseChildren If <code>true</code>, the visitor will
     * visit children, otherwise only the top level nodes are recursed.
     */
    public NodeVisitor (boolean recurseChildren)
    {
        this (recurseChildren, true);
    }
    
    /**
     * Creates a node visitor that recurses itself only if
     * <code>recurseSelf</code> is <code>true</code> and it's children
     * only if <code>recurseChildren</code> is <code>true</code>.
     * @param recurseChildren If <code>true</code>, the visitor will
     * visit children, otherwise only the top level nodes are recursed.
     * @param recurseSelf If <code>true</code>, the visitor will
     * visit the top level node.
     */
    public NodeVisitor (boolean recurseChildren, boolean recurseSelf)
    {
        mRecurseChildren = recurseChildren;
        mRecurseSelf = recurseSelf;
    }

    /**
     * Override this method if you wish to do special
     * processing prior to the start of parsing.
     */
    public void beginParsing ()
    {
    }

    /**
     * Called for each <code>Tag</code> visited.
     * @param tag The tag being visited.
     */
    public void visitTag (Tag tag)
    {
    }
    
    /**
     * Called for each <code>Tag</code> visited that is an end tag.
     * @param tag The end tag being visited.
     */
    public void visitEndTag (Tag tag)
    {
    }
    
    /**
     * Called for each <code>StringNode</code> visited.
     * @param string The string node being visited.
     */
    public void visitStringNode (Text string)
    {
    }
    
    /**
     * Called for each <code>RemarkNode</code> visited.
     * @param remark The remark node being visited.
     */
    public void visitRemarkNode (Remark remark)
    {
    }

    /**
     * Override this method if you wish to do special
     * processing upon completion of parsing.
     */
    public void finishedParsing ()
    {
    }

    /**
     * Depth traversal predicate.
     * @return <code>true</code> if children are to be visited.
     */
    public boolean shouldRecurseChildren ()
    {
        return (mRecurseChildren);
    }
    
    /**
     * Self traversal predicate.
     * @return <code>true</code> if a node itself is to be visited.
     */
    public boolean shouldRecurseSelf ()
    {
        return (mRecurseSelf);
    }
}
