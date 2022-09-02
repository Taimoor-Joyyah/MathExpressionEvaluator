package com.company.operation;

public enum ComponentType {
    DOUBLE,
    OPERATOR,
    NEGATIVE,
    OPEN,
    CLOSE
}
// Operator -> DOUBLE, CLOSE
// NEGATIVE -> OPERATOR, OPEN
// DOUBLE -> OPERATOR, NEGATIVE, OPEN
// OPEN -> OPERATOR, OPEN
// CLOSE -> DOUBLE, CLOSE

//    X     OPERATOR    NEGATIVE    DOUBLE  OPEN    CLOSE
// OPERATOR    N            N          Y      N       Y
// NEGATIVE    Y            N          N      Y       N
// DOUBLE      Y            Y          N      Y       N
// OPEN        Y            N          N      Y       N
// CLOSE       N            N          Y      N       Y