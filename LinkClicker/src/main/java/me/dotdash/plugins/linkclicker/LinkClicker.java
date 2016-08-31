/* The MIT License (MIT)
 *
 * Copyright (c) DotDash
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package me.dotdash.plugins.linkclicker;

import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.message.MessageEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;

import java.net.MalformedURLException;
import java.net.URL;

@Plugin(id = "dotdash-linkclicker", name = "LinkClicker", version = "1.0.1")
public class LinkClicker {
    @Subscribe(order = Order.LATE)
    public void onPlayerChat(MessageEvent event) {
        String msg = Texts.toPlain(event.getMessage()).toLowerCase();
        if(msg.contains("http://") || msg.contains("https://")) {
            String link;
            if(msg.substring(msg.indexOf("http")).contains(" ")) {
                link = msg.substring(msg.indexOf("http"), msg.indexOf(" ", msg.indexOf("http")));
            } else {
                link = msg.substring(msg.indexOf("http"));
            }
            try {
                event.setNewMessage(Texts.builder().append(event.getMessage()).onClick(TextActions.openUrl(new URL(link))).build());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}