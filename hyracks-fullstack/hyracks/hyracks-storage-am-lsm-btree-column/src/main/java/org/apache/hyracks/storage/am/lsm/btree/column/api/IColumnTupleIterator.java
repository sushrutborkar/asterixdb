/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.hyracks.storage.am.lsm.btree.column.api;

import org.apache.hyracks.api.exceptions.HyracksDataException;
import org.apache.hyracks.storage.am.common.api.ILSMIndexCursor;
import org.apache.hyracks.storage.am.lsm.common.api.ILSMComponent;
import org.apache.hyracks.storage.am.lsm.common.api.ILSMTreeTupleReference;
import org.apache.hyracks.storage.common.buffercache.IBufferCache;
import org.apache.hyracks.storage.common.buffercache.ICachedPage;

/**
 * A tuple representation that combines all the columns. It simply provides a way to iterate over tuples for a given
 * set that could span multiple pages.
 */
public interface IColumnTupleIterator extends ILSMTreeTupleReference, Comparable<IColumnTupleIterator> {
    /**
     * Reset the iterator starting at the provided index
     *
     * @param startIndex start from the tuple at this index
     */
    void reset(int startIndex) throws HyracksDataException;

    /**
     * Mark {@link IColumnTupleIterator} as consumed
     */
    void consume();

    /**
     * @return true if the {@link IColumnTupleIterator} is consumed, false otherwise
     */
    boolean isConsumed();

    /**
     * Skip a number of tuples
     *
     * @param count the number of tuples that needed to be skipped
     */
    void skip(int count) throws HyracksDataException;

    /**
     * Move to the next tuple
     */
    void next() throws HyracksDataException;

    /**
     * Notifies that the last tuple has been consumed
     */
    void lastTupleReached() throws HyracksDataException;

    /**
     * The component index is the same as the index of a component in an {@link ILSMIndexCursor}
     *
     * @return From which {@link ILSMComponent} this iterator is for
     */
    int getComponentIndex();

    /**
     * Calls {@link IBufferCache#unpin(ICachedPage)} for all columns' pages
     */
    void unpinColumnsPages() throws HyracksDataException;
}
