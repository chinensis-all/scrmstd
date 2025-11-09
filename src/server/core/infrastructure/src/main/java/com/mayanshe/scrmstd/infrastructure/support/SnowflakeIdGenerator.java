/*
 * [ScrmStd] - 通用SCRM系统
 * Copyright (C) [2025] [张西海]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.mayanshe.scrmstd.infrastructure.support;

import com.mayanshe.scrmstd.shared.contract.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 雪花算法ID生成器
 */

public class SnowflakeIdGenerator implements IdGenerator {
    private static final long EPOCH = 1759276800000L;           // 2025-10-01 00:00:00 UTC
    private static final int MAX_CLOCK_BACKWARD_MS = 1000;      // 容忍最大回拨时间 1秒
    private static final long TIMESTAMP_BITS = 41L;
    private static final long MACHINE_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_ID_BITS);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    private static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = MACHINE_ID_SHIFT + MACHINE_ID_BITS;
    private final long machineId;
    private final AtomicLong lastTimestamp = new AtomicLong(-1L);
    private final AtomicLong sequence = new AtomicLong(0L);
    private final AtomicLong clockBackwardCount = new AtomicLong(0L);

    // ===== 构造函数 =====
    public SnowflakeIdGenerator(long machineId) {
        if (machineId > MAX_MACHINE_ID || machineId < 0) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + MAX_MACHINE_ID);
        }
        this.machineId = machineId;
    }

    // ===== 生成 long 类型 ID =====
    public long nextId() {
        while (true) {
            long currentTimestamp = System.currentTimeMillis();
            long lastTs = lastTimestamp.get();

            // 检查时钟回拨
            if (currentTimestamp < lastTs) {
                long offset = lastTs - currentTimestamp;
                if (offset <= MAX_CLOCK_BACKWARD_MS) {
                    try {
                        Thread.sleep(offset);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Clock backward wait interrupted", e);
                    }
                    clockBackwardCount.incrementAndGet();
                    continue;
                } else {
                    currentTimestamp = lastTs; // 超过容忍范围，使用逻辑时钟
                }
            }

            long seq = sequence.get();
            if (currentTimestamp == lastTs) {
                long nextSeq = (seq + 1) & MAX_SEQUENCE;
                if (nextSeq == 0) {
                    currentTimestamp = waitNextMillis(lastTs);
                }
                if (sequence.compareAndSet(seq, nextSeq)) {
                    lastTimestamp.set(currentTimestamp);
                    return composeId(currentTimestamp, machineId, nextSeq);
                }
            } else {
                if (lastTimestamp.compareAndSet(lastTs, currentTimestamp)) {
                    sequence.set(0L);
                    return composeId(currentTimestamp, machineId, 0L);
                }
            }
        }
    }

    private long waitNextMillis(long lastTs) {
        long ts = System.currentTimeMillis();
        while (ts <= lastTs) {
            ts = System.currentTimeMillis();
        }
        return ts;
    }

    private long composeId(long timestamp, long machineId, long sequence) {
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) | (machineId << MACHINE_ID_SHIFT) | sequence;
    }

    public long getClockBackwardCount() {
        return clockBackwardCount.get();
    }
}
