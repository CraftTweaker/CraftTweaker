package com.blamejared.crafttweaker.impl.script.recipefs;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class RecipeFileChannel extends FileChannel {
    
    private final Lock lock;
    private final byte[] contents;
    private final int length;
    
    private int position;
    
    private RecipeFileChannel(final byte[] contents) {
        
        this.lock = new ReentrantLock();
        this.length = (this.contents = contents).length;
        this.position = 0;
    }
    
    static FileChannel of(final RecipeFsResolver.Bound resolver, final Set<? extends OpenOption> options, final FileAttribute<?>... attributes) throws IOException {
        
        verifyFlags(options);
        Objects.requireNonNull(attributes);
        final String contents = resolver.resolveContents();
        return new RecipeFileChannel(contents.getBytes(StandardCharsets.UTF_8));
    }
    
    private static void verifyFlags(final Set<? extends OpenOption> options) {
        
        if(options.isEmpty()) {
            return;
        }
        final Set<? extends OpenOption> newOptions = new HashSet<>(options);
        newOptions.remove(StandardOpenOption.READ);
        if(!newOptions.isEmpty()) {
            throw new UnsupportedOperationException("Read-only file system does not support options " + newOptions);
        }
    }
    
    @Override
    public int read(final ByteBuffer dst) throws IOException {
        
        Objects.requireNonNull(dst);
        if(dst.isReadOnly()) {
            throw new IllegalArgumentException("Unable to fill a read-only buffer");
        }
        this.open();
        this.lock.lock();
        try {
            final int available = this.length - this.position;
            
            if(available <= 0) {
                return -1;
            }
            
            final int remaining = dst.remaining();
            final int readAmount = Math.min(available, remaining);
            dst.put(this.contents, this.position, readAmount);
            
            this.position += readAmount;
            return readAmount;
        } catch(final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffer", e);
        } finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public long read(final ByteBuffer[] dsts, final int offset, final int length) throws IOException {
        
        Objects.requireNonNull(dsts);
        Objects.checkFromIndexSize(offset, length, dsts.length);
        for(final ByteBuffer buffer : dsts) {
            if(buffer.isReadOnly()) {
                throw new IllegalArgumentException("Unable to fill a read-only buffer");
            }
        }
        this.open();
        this.lock.lock();
        try {
            int available = this.length - this.position;
            
            if(available <= 0) {
                return -1;
            }
            
            long rollingTotal = 0;
            
            for(int i = offset, l = offset + length; i < l; ++i) {
                final ByteBuffer buffer = dsts[i];
                final int remainingForBuffer = buffer.remaining();
                final int readAmount = Math.min(available, remainingForBuffer);
                buffer.put(this.contents, this.position, readAmount);
                this.position += readAmount;
                rollingTotal += readAmount;
                available = this.length - this.position;
            }
            
            return rollingTotal;
        } catch(final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffers", e);
        } finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public int write(final ByteBuffer src) throws IOException {
        
        Objects.requireNonNull(src);
        this.open();
        throw new NonWritableChannelException();
    }
    
    @Override
    public long write(final ByteBuffer[] srcs, final int offset, final int length) throws IOException {
        
        Objects.requireNonNull(srcs);
        Objects.checkFromIndexSize(offset, length, srcs.length);
        this.open();
        throw new NonWritableChannelException();
    }
    
    @Override
    public long position() throws IOException {
        
        this.open();
        return this.position;
    }
    
    @Override
    public FileChannel position(final long newPosition) throws IOException {
        
        if(newPosition < 0 || newPosition > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Unable to set position " + newPosition);
        }
        this.open();
        this.lock.lock();
        try {
            this.position = (int) newPosition;
            return this;
        } finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public long size() throws IOException {
        
        this.open();
        return this.length;
    }
    
    @Override
    public FileChannel truncate(final long size) throws IOException {
        
        if(size < 0 || size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Unable to truncate to " + size);
        }
        this.open();
        throw new NonWritableChannelException();
    }
    
    @Override
    public void force(final boolean metaData) throws IOException {
        
        this.open();
    }
    
    @Override
    public long transferTo(final long position, final long count, final WritableByteChannel target) throws IOException {
        
        Objects.requireNonNull(target);
        if(position < 0 || count < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.open();
        this.lock.lock();
        try {
            final ByteBuffer readBuffer = ByteBuffer.allocate((int) count).order(ByteOrder.nativeOrder());
            final long available = this.length - position;
            final int readAmount = (int) Math.min(available, count);
            readBuffer.put(this.contents, (int) position, readAmount);
            readBuffer.flip();
            return target.write(readBuffer);
        } catch(final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to transfer from resource to channel", e);
        } finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public long transferFrom(final ReadableByteChannel src, final long position, final long count) throws IOException {
        
        Objects.requireNonNull(src);
        if(position < 0 || count < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.open();
        throw new NonWritableChannelException();
    }
    
    @Override
    public int read(final ByteBuffer dst, final long position) throws IOException {
        
        Objects.requireNonNull(dst);
        if(dst.isReadOnly()) {
            throw new IllegalArgumentException("Unable to fill a read-only buffer");
        }
        if(position < 0 || position > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid position " + position);
        }
        
        final int intPosition = (int) position;
        this.open();
        this.lock.lock();
        try {
            final int available = this.length - intPosition;
            
            if(available <= 0) {
                return -1;
            }
            
            final int remaining = dst.remaining();
            final int readAmount = Math.min(available, remaining);
            dst.put(this.contents, intPosition, readAmount);
            return readAmount;
        } catch(final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffer", e);
        } finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public int write(final ByteBuffer src, final long position) throws IOException {
        
        Objects.requireNonNull(src);
        if(position < 0 || position > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid position " + position);
        }
        this.open();
        throw new NonWritableChannelException();
    }
    
    @Override
    public MappedByteBuffer map(final MapMode mode, final long position, final long size) throws IOException {
        
        Objects.requireNonNull(mode);
        if(position < 0 || size < 0 || size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Mapped buffers not supported"));
    }
    
    @Override
    public FileLock lock(long position, long size, boolean shared) throws IOException {
        
        if(position < 0 || size < 0 || (position + size) < 0) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Locks not supported"));
    }
    
    @Override
    public FileLock tryLock(long position, long size, boolean shared) throws IOException {
        
        if(position < 0 || size < 0 || (position + size) < 0) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Locks not supported"));
    }
    
    @Override
    protected void implCloseChannel() {}
    
    private void open() throws IOException {
        
        if(!this.isOpen()) {
            throw new ClosedChannelException();
        }
    }
    
}
