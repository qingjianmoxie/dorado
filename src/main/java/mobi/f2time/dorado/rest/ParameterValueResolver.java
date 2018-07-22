/*
 * Copyright 2017 The OpenDSP Project
 *
 * The OpenDSP Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package mobi.f2time.dorado.rest;

import java.io.InputStream;

import mobi.f2time.dorado.rest.servlet.HttpRequest;
import mobi.f2time.dorado.rest.servlet.HttpResponse;
import mobi.f2time.dorado.rest.util.ClassUtils;
import mobi.f2time.dorado.rest.util.MethodDescriptor;
import mobi.f2time.dorado.rest.util.MethodDescriptor.MethodParameter;
import mobi.f2time.dorado.rest.util.TypeConverter;
import mobi.f2time.dorado.rest.util.TypeConverters;

/**
 * 
 * @author wangwp
 */
public interface ParameterValueResolver {

	Object resolveParameterValue(HttpRequest request, HttpResponse response, MethodDescriptor desc,
			MethodParameter methodParameter, String pathVariable);

	@SuppressWarnings("unchecked")
	ParameterValueResolver REQUEST_PARAM = (req, resp, methodDesc, methodParam, pathVariable) -> TypeConverters
			.resolveConverter(methodParam.getType()).convert(req.getParameter(methodParam.getName()));

	@SuppressWarnings("unchecked")
	ParameterValueResolver HEADER_PARAM = (req, resp, methodDesc, methodParam, pathVariable) -> TypeConverters
			.resolveConverter(methodParam.getType()).convert(req.getHeader(methodParam.getName()));

	@SuppressWarnings("unchecked")
	ParameterValueResolver PATH_PARAM = (req, resp, methodDesc, methodParam, pathVariable) -> TypeConverters
			.resolveConverter(methodParam.getType()).convert(pathVariable);

	ParameterValueResolver HTTP_REQUEST = (req, resp, methodDesc, methodParam, pathVariable) -> TypeConverter.DUMMY
			.convert(req);

	ParameterValueResolver HTTP_RESPONSE = (req, resp, methodDesc, methodParam, pathVariable) -> TypeConverter.DUMMY
			.convert(resp);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	ParameterValueResolver REQUEST_BODY = (req, resp, methodDesc, methodParam, pathVariable) -> {
		Class<?> parameterType = methodParam.getType();
		if (ClassUtils.isStringOrPrimitive(parameterType) && parameterType != String.class) {
			throw new IllegalArgumentException("invalid parameter type");
		}

		InputStream payload = req.getInputStream();

		MessageBodyConverter converter = MessageBodyConverters.getMessageBodyConverter(methodDesc.consume());
		return converter.readMessageBody(payload, parameterType);

	};
}
