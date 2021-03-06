/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/

package gplx.xowa.mediawiki.vendor.wikimedia.Assert.src;

import gplx.String_;

// MW.SRC:1.33.1
/**
 * Exception indicating that a parameter element type assertion failed.
 * This generally means a disagreement between the caller and the implementation of a function.
 *
 * @license MIT
 * @author Daniel Kinzler
 * @copyright Wikimedia Deutschland e.V.
 */
public class XomwParameterElementTypeException extends XomwParameterAssertionException implements XomwAssertionException {
    /**
     * @var string
     */
    private String elementType;

    /**
     * @param string $parameterName
     * @param string $elementType
     *
     * @throws ParameterTypeException
     */
    public XomwParameterElementTypeException(String parameterName, String elementType) {
        //if ( !is_string( $elementType ) ) {
        //    throw new ParameterTypeException( 'elementType', 'string' );
        //}

        super(parameterName, String_.Format("all elements must be {0}", elementType));

        this.elementType = elementType;
    }

    /**
     * @return string
     */
    public String getElementType() {
        return this.elementType;
    }
}
