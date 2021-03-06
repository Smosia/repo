/*
 * [The "BSD license"]
 *  Copyright (c) 2010 Terence Parr
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *  1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.antlr.tool;

import org.antlr.runtime.SerializedGrammar;

import java.io.*;
import java.util.Stack;

/** Serialize a grammar into a highly compressed form with
 *  only the info needed to recognize sentences.
 *  FORMAT:
 *
 *  file ::= $ANTLR<version:byte><grammartype:byte><name:string>;<numRules:short><rules>
 *  rule ::= R<rulename:string>;B<nalts:short><alts>.
 *  alt  ::= A<elems>;
 *  elem ::= t<tokentype:short> | r<ruleIndex:short> | -<char:uchar><char:uchar> | ~<tokentype> | w
 */
public class GrammarSerializerFoo {
    protected DataOutputStream out;
    protected String filename;
    protected Grammar g;

    protected Stack streams = new Stack();
    protected ByteArrayOutputStream altBuf;
    protected int numElementsInAlt = 0;

    public GrammarSerializerFoo(Grammar g) {
        this.g = g;
    }

    public void open(String filename) throws IOException {
        this.filename = filename;
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        out = new DataOutputStream(bos);
        writeString(out, SerializedGrammar.COOKIE);
        out.writeByte(SerializedGrammar.FORMAT_VERSION);
    }

    public void close() throws IOException {
        if ( out!=null ) out.close();
        out = null;
    }


    // WRITE

    public void grammar(int grammarTokenType, String name) {
        try {
            /*
            switch ( grammarTokenType ) {
                case ANTLRParser.LEXER_GRAMMAR : out.writeByte('l'); break;
                case ANTLRParser.PARSER_GRAMMAR : out.writeByte('p'); break;
                case ANTLRParser.TREE_GRAMMAR: out.writeByte('t'); break;
                case ANTLRParser.COMBINED_GRAMMAR : out.writeByte('c'); break;
            }
            writeString(out, name);
            */
            out.writeShort(g.getRules().size());
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void rule(String name) {
        try {
            out.writeByte('R');
            writeString(out, name);
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void endRule() {
        try {
            out.writeByte('.');
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void block(int nalts) {
        try {
            out.writeByte('B');
            out.writeShort(nalts);
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void alt(GrammarAST alt) {
        numElementsInAlt = 0;
        try {
            out.writeByte('A');
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
        //streams.push(out);
        //altBuf = new ByteArrayOutputStream();
        //out = new DataOutputStream(altBuf);
    }

    public void endAlt() {
        try {
            //out.flush();
            //out = (DataOutputStream)streams.pop(); // restore previous stream
            out.writeByte(';');
            //out.writeShort(numElementsInAlt);
            //out.write(altBuf.toByteArray());
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void ruleRef(GrammarAST t) {
        numElementsInAlt++;
        try {
            out.writeByte('r');
            out.writeShort(g.getRuleIndex(t.getText()));
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void token(GrammarAST t) {
        numElementsInAlt++;
        try {
            out.writeByte('t');
            int ttype = g.getTokenType(t.getText());
            out.writeShort(ttype);
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void charLiteral(GrammarAST t) {
        numElementsInAlt++;
        try {
            if ( g.type!=Grammar.LEXER ) {
                out.writeByte('t');
                int ttype = g.getTokenType(t.getText());
                out.writeShort(ttype);
            }
            // else lexer???
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void wildcard(GrammarAST t) {
        numElementsInAlt++;
        try {
            out.writeByte('w');
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void range() { // must be char range
        numElementsInAlt++;
        try {
            out.writeByte('-');
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void not() {
        try {
            out.writeByte('~');
        }
        catch (IOException ioe) {
            ErrorManager.error(ErrorManager.MSG_CANNOT_WRITE_FILE, filename);
        }
    }

    public void writeString(DataOutputStream out, String s) throws IOException {
        out.writeBytes(s);
        out.writeByte(';');
    }
}
