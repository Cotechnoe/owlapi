package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.semanticweb.owlapi.model.OWLRuntimeException;

/** Token Manager Error. */
@SuppressWarnings("unused")
public class TokenMgrError extends OWLRuntimeException {

    /**
     * The version identifier for this Serializable class. Increment only if the
     * <i>serialized</i> form of the class changes.
     */
    private static final long serialVersionUID = 30406L;
    /*
     * Ordinals for various reasons why an Error of this type can be thrown.
     */
    /** Lexical error occurred. */
    static final int LEXICAL_ERROR = 0;
    /**
     * An attempt was made to create a second instance of a static token
     * manager.
     */
    static final int STATIC_LEXER_ERROR = 1;
    /** Tried to change to an invalid lexical state. */
    static final int INVALID_LEXICAL_STATE = 2;
    /** Detected (and bailed out of) an infinite loop in the token manager. */
    static final int LOOP_DETECTED = 3;
    /**
     * Indicates the reason why the exception is thrown. It will have one of the
     * above 4 values.
     */
    int errorCode;

    /**
     * Replaces unprintable characters by their escaped (or unicode escaped)
     * equivalents in the given string.
     * 
     * @param str
     *        string to escape
     * @return escaped string
     */
    protected static final String addEscapes(String str) {
        StringBuffer retval = new StringBuffer();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 0:
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                        String s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u"
                                + s.substring(s.length() - 4, s.length()));
                    } else {
                        retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

    /**
     * Returns a detailed message for the Error when it is thrown by the token
     * manager to indicate a lexical error. Parameters : EOFSeen : indicates if
     * EOF caused the lexical error curLexState : lexical state in which this
     * error occurred errorLine : line number when the error occurred
     * errorColumn : column number when the error occurred errorAfter : prefix
     * that was seen before this error occurred curchar : the offending
     * character Note: You can customize the lexical error message by modifying
     * this method.
     * 
     * @param EOFSeen
     *        EOFSeen
     * @param lexState
     *        lexState
     * @param errorLine
     *        errorLine
     * @param errorColumn
     *        errorColumn
     * @param errorAfter
     *        errorAfter
     * @param curChar
     *        curChar
     * @return error
     */
    protected static String LexicalError(boolean EOFSeen, int lexState,
            int errorLine, int errorColumn, String errorAfter, char curChar) {
        return "Lexical error at line "
                + errorLine
                + ", column "
                + errorColumn
                + ".  Encountered: "
                + (EOFSeen ? "<EOF> " : "\""
                        + addEscapes(String.valueOf(curChar)) + "\"" + " ("
                        + (int) curChar + "), ") + "after : \""
                + addEscapes(errorAfter) + "\"";
    }

    /**
     * You can also modify the body of this method to customize your error
     * messages. For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE
     * are not of end-users concern, so you can return something like :
     * "Internal Error : Please file a bug report .... " from this method for
     * such cases in the release version of your parser.
     * 
     * @return the message
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /*
     * Constructors of various flavors follow.
     */
    /** No arg constructor. */
    public TokenMgrError() {}

    /**
     * Constructor with message and reason.
     * 
     * @param message
     *        the message
     * @param reason
     *        the reason
     */
    public TokenMgrError(String message, int reason) {
        super(message);
        errorCode = reason;
    }

    /**
     * Full Constructor.
     * 
     * @param EOFSeen
     *        EOFSeen
     * @param lexState
     *        lexState
     * @param errorLine
     *        errorLine
     * @param errorColumn
     *        errorColumn
     * @param errorAfter
     *        errorAfter
     * @param curChar
     *        curChar
     * @param reason
     *        reason
     */
    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine,
            int errorColumn, String errorAfter, char curChar, int reason) {
        this(LexicalError(EOFSeen, lexState, errorLine, errorColumn,
                errorAfter, curChar), reason);
    }
}
