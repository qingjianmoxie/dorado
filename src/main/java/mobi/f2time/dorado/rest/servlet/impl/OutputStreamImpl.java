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
package mobi.f2time.dorado.rest.servlet.impl;

import java.io.IOException;
import java.io.OutputStream;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 
 * @author wangwp
 */
public class OutputStreamImpl extends OutputStream {
	private ByteBufOutputStream out;
	
	public OutputStreamImpl(FullHttpResponse response) {
		this.out = new ByteBufOutputStream(response.content());
	}
	
	@Override
	public void write(int b) throws IOException {
		this.out.write(b);
	}
}
