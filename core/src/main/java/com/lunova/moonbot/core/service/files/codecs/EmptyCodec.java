package com.lunova.moonbot.core.service.files.codecs;

import com.lunova.moonbot.core.service.files.FormatCodec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyCodec extends FormatCodec {

    private static final Logger logger = LoggerFactory.getLogger(EmptyCodec.class);

    @Override
    public void encode() {}

    @Override
    public void decode() {}
}
