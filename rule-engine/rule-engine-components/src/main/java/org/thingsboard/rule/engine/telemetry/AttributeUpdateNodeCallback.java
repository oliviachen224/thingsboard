/**
 * Copyright © 2016-2022 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.rule.engine.telemetry;

import org.thingsboard.rule.engine.api.TbContext;
import org.thingsboard.server.common.data.kv.AttributeKvEntry;
import org.thingsboard.server.common.msg.TbMsg;

import javax.annotation.Nullable;
import java.util.List;

public class AttributeUpdateNodeCallback extends TelemetryNodeCallback {

    private String scope;
    private List<AttributeKvEntry> attributes;

    public AttributeUpdateNodeCallback(TbContext ctx, TbMsg msg, String scope, List<AttributeKvEntry> attributes) {
        super(ctx, msg);
        this.scope = scope;
        this.attributes = attributes;
    }

    @Override
    public void onSuccess(@Nullable Void result) {
        TbContext ctx = this.getCtx();
        TbMsg tbMsg = this.getMsg();
        ctx.enqueue(ctx.attributeUpdateActionMsg(tbMsg.getOriginator(), ctx.getSelfId(), scope, attributes),
                () -> ctx.tellSuccess(tbMsg),
                throwable -> ctx.tellFailure(tbMsg, throwable));
    }
}
