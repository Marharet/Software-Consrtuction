package org.src.Errors;

/**
 * Enumeration of error types
 */
public enum ErrorKind {
     SYNTAX,
     UNBALPARENS,
     NOEXP,
     DIVBYZERO,
     EQUALEXPECTED,
     NOTVAR,
     LABELTABLEFULL,
     DUPLABEL,
     UNDEFLABEL,
     THENEXPECTED,
     TOEXPECTED,
     NEXTWITHOUTFOR,
     RETURNWITHOUTGOSUB,
     MISSINGQUOTE,
     FILENOTFOUND,
     FILEIOERROR,
     INPUTIOERROR
}
