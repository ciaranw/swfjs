package com.ciaranwood.swfjs;

import java.math.BigDecimal;

public class Transform {

    public BigDecimal a, b, c, d, e, f;

    public Transform normalise() {
        Transform normalised = new Transform();
        normalised.a = (a == null) ? new BigDecimal("1") : a;
        normalised.b = (b == null) ? BigDecimal.ZERO : b;
        normalised.c = (c == null) ? BigDecimal.ZERO : c;
        normalised.d = (d == null) ? new BigDecimal("1") : d;
        normalised.e = e;
        normalised.f = f;

        return normalised;
    }

}
